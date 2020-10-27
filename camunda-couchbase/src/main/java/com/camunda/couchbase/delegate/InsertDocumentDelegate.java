package com.camunda.couchbase.delegate;

import static org.camunda.spin.Spin.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.camunda.couchbase.model.RuleSet;
import com.camunda.couchbase.service.CouchbaseQueryService;

public class InsertDocumentDelegate implements JavaDelegate {

    @Autowired
    CouchbaseQueryService couchbaseQueryService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Map<String, Object> responseData = new HashMap<>();
		responseData.put("retrivedDocument", execution.getVariable("retrivedDocument"));
		/*execution.getVariables().forEach((key, value) -> {
			if (key.equals("retrivedDocument")) {
				responseData.put("retrivedDocument", value);
			}
		});*/
		if (!responseData.isEmpty()) {
			Map<String, Object> data = getData(responseData);
			Map<String, Object> insertedData = new HashMap<>();
			log.info("After calling getInsertedData");
			if (!data.isEmpty()) {
				//log.info("processInstanceId {}", execution.getProcessInstanceId());
				data.put("processInstanceId", execution.getProcessInstanceId());
				/*log.info("Calling couchbase service");
				/insertedData.put("insertedData", data);
				execution.setVariables(insertedData);*/
				//couchbaseQueryService.insertDoc(data);
				String json = JSON(data).toString();
				log.info("FINAL JSON {}", json);
				execution.setVariable("insertedData", json);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getData(Map<String, Object> retrivedDoc) {
		//retrivedDoc.put("pmGroup", "PM Group");
		log.info("In getInsertedData {}", retrivedDoc);
		
		String json = JSON(retrivedDoc.get("retrivedDocument")).toString();
		//log.info("json {}", json);		
		Map<String, Object> responseData = JSON(json).mapTo(Map.class);
		//log.info("Map responseData {}", responseData);
		Map<String, Object> data = new HashMap<>();
		((Map<String, Object>) responseData.get("data")).forEach((dataKey, dataValue) -> data.put(dataKey, dataValue));
		/*responseData.forEach((key, value) -> {
			if (key.equals("data")) {
				data.put(key, value);
			}
		});*/
		data.put("pmGroup", "PM Group 1");
		data.put("type", "preProcessingTest");
		return data;
	}

}
