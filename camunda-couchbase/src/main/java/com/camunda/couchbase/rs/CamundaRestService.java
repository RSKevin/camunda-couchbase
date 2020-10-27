package com.camunda.couchbase.rs;

import com.camunda.couchbase.model.AppResponse;
import com.camunda.couchbase.model.RuleSet;
import com.camunda.couchbase.model.PreProcessingRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface CamundaRestService {
	
	@PostMapping("/process/{key}/start")
	public AppResponse startProcessByKey(@PathVariable String key);
	
	@PostMapping("/task/{processInstanceId}")
	public AppResponse getTask(@PathVariable String processInstanceId);

	@PostMapping("/task/{taskId}/complete")
	public AppResponse completeTask(@PathVariable String taskId);
	
	@PostMapping("/preprocessing/complete-process")
	public AppResponse preProcessing();
	
	@PostMapping("/spin")
	public AppResponse getSpin(@RequestBody RuleSet ruleSet);
	
	@PostMapping("/preprocessing")
	public AppResponse preProcessing(@RequestBody PreProcessingRequest preProcessingRequest);
	
}