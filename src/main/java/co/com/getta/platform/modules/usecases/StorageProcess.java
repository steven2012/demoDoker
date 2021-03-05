package co.com.getta.platform.modules.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.getta.platform.crosscutting.domain.RulesDto;
import co.com.getta.platform.modules.common.ApiResponse;
import co.com.getta.platform.modules.dataprovider.AzureDocumentDataProvider;

@Component
public class StorageProcess {

	@Autowired
	private AzureDocumentDataProvider storageProcess;

	public ApiResponse<RulesDto> tableProcess(String keyTournament) {

		return storageProcess.getRule(keyTournament);
	}

}
