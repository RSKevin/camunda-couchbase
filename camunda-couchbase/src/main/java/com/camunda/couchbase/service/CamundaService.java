package com.camunda.couchbase.service;

import com.camunda.couchbase.model.PreProcessingRequest;
import com.camunda.couchbase.model.PreProcessingResponse;
import com.camunda.couchbase.model.RuleSet;

public interface CamundaService {

	public Object startProcessByKey(String key);

	public Object taskByProcessInstanceId(String processInstanceId);

	public Object completeTask(String taskId);

	public PreProcessingResponse preProcessing();

	public RuleSet getSpin(RuleSet ruleSet);

	public RuleSet preProcessing(PreProcessingRequest preProcessingRequest);

}