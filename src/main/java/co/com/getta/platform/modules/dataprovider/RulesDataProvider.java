package co.com.getta.platform.modules.dataprovider;

import co.com.getta.platform.crosscutting.domain.RulesDto;

public interface RulesDataProvider {

	public RulesDto getRule(String id, String awards) throws Exception;

}
