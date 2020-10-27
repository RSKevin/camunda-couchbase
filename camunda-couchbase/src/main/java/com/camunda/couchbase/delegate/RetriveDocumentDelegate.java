package com.camunda.couchbase.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.camunda.couchbase.service.CouchbaseQueryService;

public class RetriveDocumentDelegate implements JavaDelegate {
	
    @Autowired
    CouchbaseQueryService couchbaseQueryService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		execution.getVariables().forEach((key, value) -> log.info("{} : {}", key, value));
		String key = (String) execution.getVariable("documentKey");
		log.info("Key : {}", key);
		execution.setVariable("outputData", couchbaseQueryService.getByKey(key));
	}

}
