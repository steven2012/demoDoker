package co.com.getta.platform.modules.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.getta.platform.crosscutting.domain.RulesDto;
import co.com.getta.platform.crosscutting.payload.GetRulesStoragePayload;
import co.com.getta.platform.modules.common.ApiResponse;
import co.com.getta.platform.modules.usecases.StorageProcess;

@RestController
@RequestMapping(value = "/Games", produces = { MediaType.APPLICATION_JSON_VALUE })
public class RulesWebApi {

	@Autowired
	private StorageProcess storageProcess;

	@GetMapping("/tournament-rules")
	public ApiResponse<RulesDto> tableStorge(GetRulesStoragePayload payload) {
		return storageProcess.tableProcess(payload.getKeyTournament());
	}
}
