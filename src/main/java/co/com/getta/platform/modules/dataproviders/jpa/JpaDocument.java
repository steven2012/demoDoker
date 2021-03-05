package co.com.getta.platform.modules.dataproviders.jpa;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.EntityProperty;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableResult;

import co.com.getta.platform.crosscutting.constants.GetaConstants;
import co.com.getta.platform.crosscutting.domain.ItemsRulesDto;
import co.com.getta.platform.crosscutting.domain.RulesDto;
import co.com.getta.platform.crosscutting.persistence.entity.Document;
import co.com.getta.platform.crosscutting.util.MessageUtil;
import co.com.getta.platform.modules.common.ApiResponse;
import co.com.getta.platform.modules.dataprovider.AzureDocumentDataProvider;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JpaDocument<T> implements AzureDocumentDataProvider {

	@Autowired
	private CloudStorageAccount storageAcount;

	@Autowired
	private MessageUtil messagesUtil;

	@Autowired
	private ObjectMapper mapper;

	@Value(GetaConstants.PARTITION_KEY)
	private String partitionKey;

	@Value(GetaConstants.ROW_KEY)
	private String rowKey;

	@Override
	public ApiResponse<RulesDto> getRule(String keyTournament) {
		RulesDto rule = new RulesDto();
		ApiResponse<RulesDto> response = new ApiResponse<>();

		CloudTableClient tableClient = storageAcount.createCloudTableClient();
		try {

			CloudTable cloudTable = tableClient.getTableReference(GetaConstants.NAME_TABLE_RULES);

			String partitioKey = partitionKey;
			String rowkey = rowKey;
			TableOperation table = TableOperation.retrieve(partitioKey, rowkey, Document.class);
			TableResult result = cloudTable.execute(table);
			HashMap<String, EntityProperty> map = result.getProperties();
			EntityProperty property = map.get(GetaConstants.KEY_JSON_RULES);
			String jsonRules = property.getValueAsString();

			// Obtain object JsoNode
			JsonNode rul = mapper.readValue(jsonRules, JsonNode.class);

			// Converter to Object
			Object item = mapper.convertValue(rul.get(GetaConstants.KEY_ROOT_JSON), Object.class);
			rule = buildDto(item, keyTournament, rule);
			if (rule == null) {
				response.setData(rule);
				response.setSuccess(false);
				response.setStatusCode(HttpStatus.PRECONDITION_FAILED.value());
				response.setMessage(messagesUtil.getMessage(GetaConstants.KEY_NOT_FOUND));
				return response;
			}
			response.setData(rule);
			response.setSuccess(true);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("");

			return response;

		} catch (URISyntaxException | StorageException | IOException e) {
			response.setData(rule);
			response.setSuccess(false);
			response.setStatusCode(HttpStatus.BAD_REQUEST.value());
			response.setMessage(messagesUtil.getMessage(GetaConstants.BAD_REQUEST));
			return response;

		}
	}

	@SuppressWarnings("unchecked")
	private RulesDto buildDto(Object item, String keyTournament, RulesDto rule) {

		if (item instanceof LinkedHashMap) {

			LinkedHashMap<String, Object> details = new LinkedHashMap<>();

			LinkedHashMap<?, ?> linked = (LinkedHashMap<?, ?>) item;

			List<String> keys = new ArrayList(linked.keySet());

			if (!keys.contains(keyTournament)) {
				return null;
			}

			LinkedHashMap<?, ?> tournament = (LinkedHashMap<?, ?>) linked.get(keyTournament);
			List<String> keysTournament = new ArrayList(tournament.keySet());

			// Create Map info
			ItemsRulesDto items = new ItemsRulesDto();

			builMap(details, keysTournament, tournament);

			LinkedHashMap<String, Object> contentListItem = new LinkedHashMap<>();
			contentListItem.put("List", details);

			items.setDetails(details);
			rule.setRules(items);

			log.info(" vamos coronando");
		}

		return rule;
	}

	private void builMap(LinkedHashMap<String, Object> details, List<String> keys, LinkedHashMap<?, ?> tournament) {

		for (String item : keys) {
			details.put(item, tournament.get(item));
		}
	}

}
