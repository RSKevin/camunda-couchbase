package com.camunda.couchbase.rs.impl;

import com.camunda.couchbase.config.AppConstants;
import com.camunda.couchbase.model.AppResponse;
import com.camunda.couchbase.rs.DocumentRestService;
import com.camunda.couchbase.service.CouchbaseQueryService;

import static org.camunda.spin.Spin.JSON;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentRestServiceImpl implements DocumentRestService {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
    CouchbaseQueryService couchbaseQueryService;

	@Override
	public AppResponse getByKey(String key) {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.SUCCESS_CODE);
		appResponse.setErrorFlag(false);
		appResponse.setData(couchbaseQueryService.getByKey(key));
		return appResponse;
	}

	@Override
	public AppResponse getByType(String type) {
		log.info("Before calling query service");
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.SUCCESS_CODE);
		appResponse.setErrorFlag(false);
		appResponse.setData(couchbaseQueryService.getByType(type));
		return appResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public AppResponse upsertDoc(Object inputPayload) {
		//log.info("Before calling query service {}", inputPayload);
		//String pyload = (String) inputPayload;
		log.info("String pyload {}", inputPayload);
		//Map<String, Object> mapPayload = JSON(inputPayload).mapTo(Map.class);
		Map<String, Object> data = new HashMap<>();
		((Map<String, Object>) inputPayload).forEach((dataKey, dataValue) -> data.put(dataKey, dataValue));
		//Map<String, Object> mapPayload = (Map<String, Object>) inputPayload;
		log.info("Map pyload {}", data.getClass());
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.SUCCESS_CODE);
		appResponse.setErrorFlag(false);
		appResponse.setData(couchbaseQueryService.upsertDoc(data));
		return appResponse;
	}

}
