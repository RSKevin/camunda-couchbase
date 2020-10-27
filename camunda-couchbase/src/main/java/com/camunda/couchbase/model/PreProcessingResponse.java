package com.camunda.couchbase.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreProcessingResponse {

	private String processInstanceId;
	private String businessKey;
	private String taskId;
	private String taskName;

}
