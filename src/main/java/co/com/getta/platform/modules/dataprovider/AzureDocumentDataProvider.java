package co.com.getta.platform.modules.dataprovider;

import co.com.getta.platform.crosscutting.domain.RulesDto;
import co.com.getta.platform.modules.common.ApiResponse;

public interface AzureDocumentDataProvider {

	public ApiResponse<RulesDto> getRule(String keyTournament);

}
