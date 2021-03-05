package co.com.getta.platform.crosscutting.persistence.entity;

import com.microsoft.azure.storage.table.TableServiceEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document extends TableServiceEntity {

	private String partitionKey;
	private String rowKey;
	private String data;

}
