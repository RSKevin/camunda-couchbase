package com.camunda.couchbase.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PreProcessingRequest {
	private String documentKey;
}