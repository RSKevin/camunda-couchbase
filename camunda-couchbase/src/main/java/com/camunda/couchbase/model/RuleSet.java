package com.camunda.couchbase.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RuleSet {

	private String name;
	private String type;
	private String id;

}