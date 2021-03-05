package co.com.getta.platform.crosscutting.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class GetRulesStoragePayload {

	private String keyTournament;

}
