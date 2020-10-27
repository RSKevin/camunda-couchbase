package com.camunda.couchbase.rs.impl;

import com.camunda.couchbase.config.AppConstants;
import com.camunda.couchbase.model.AppResponse;
import com.camunda.couchbase.model.PreProcessingRequest;
import com.camunda.couchbase.model.RuleSet;
import com.camunda.couchbase.rs.CamundaRestService;
import com.camunda.couchbase.service.CamundaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CamundaRestServiceImpl implements CamundaRestService {

	@Autowired
	private CamundaService camundaService;

	@Override
	public AppResponse startProcessByKey(String key) {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.SUCCESS_CODE);
		appResponse.setErrorFlag(false);
		appResponse.setData(camundaService.startProcessByKey(key));
		return appResponse;
	}

	@Override
	public AppResponse getTask(String processInstanceId) {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.SUCCESS_CODE);
		appResponse.setErrorFlag(false);
		appResponse.setData(camundaService.taskByProcessInstanceId(processInstanceId));
		return appResponse;
	}

	@Override
	public AppResponse completeTask(String taskId) {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.SUCCESS_CODE);
		appResponse.setErrorFlag(false);
		appResponse.setData(camundaService.completeTask(taskId));
		return appResponse;
	}

	@Override
	public AppResponse preProcessing() {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.SUCCESS_CODE);
		appResponse.setErrorFlag(false);
		appResponse.setData(camundaService.preProcessing());
		return appResponse;
	}

	@Override
	public AppResponse getSpin(RuleSet ruleSet) {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.SUCCESS_CODE);
		appResponse.setErrorFlag(false);
		appResponse.setData(camundaService.getSpin(ruleSet));
		return appResponse;
	}

	@Override
	public AppResponse preProcessing(PreProcessingRequest preProcessingRequest) {
		AppResponse appResponse = new AppResponse();
		appResponse.setResponseCode(AppConstants.SUCCESS_CODE);
		appResponse.setErrorFlag(false);
		appResponse.setData(camundaService.preProcessing(preProcessingRequest));
		return appResponse;
	}

}
