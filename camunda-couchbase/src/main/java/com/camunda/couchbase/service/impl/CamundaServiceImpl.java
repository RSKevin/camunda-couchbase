package com.camunda.couchbase.service.impl;

import com.camunda.couchbase.model.PreProcessingRequest;
import com.camunda.couchbase.model.PreProcessingResponse;
import com.camunda.couchbase.model.RuleSet;
import com.camunda.couchbase.service.CamundaService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.camunda.spin.Spin.JSON;

import java.util.HashMap;
import java.util.Map;

@Service
public class CamundaServiceImpl implements CamundaService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Override
	public Object startProcessByKey(String key) {
		log.info("In startProcessByKey method Impl for {}", key);
		//ProcessInstance instance = runtimeService.startProcessInstanceByKey(key);
		String processInstanceId = runtimeService.startProcessInstanceByKey(key).getProcessInstanceId();
	    log.info("Process started with id : {}", processInstanceId);	    
		return processInstanceId;
	}

	@Override
	public Object taskByProcessInstanceId(String processInstanceId) {
		//Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
	    //taskService.complete(task.getId());
	    log.info("In taskByProcessInstanceId method Impl for {}", processInstanceId);
		return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult().getId();
	}

	@Override
	public Object completeTask(String taskId) {
		taskService.complete(taskId);
		return "Task Completed";
	}

	@Override
	public PreProcessingResponse preProcessing() {
		PreProcessingResponse preProcessingResponse = new PreProcessingResponse();
		log.info("In preProcessing method Impl");
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("aci-camunda-couchbase-process");
		preProcessingResponse.setProcessInstanceId(instance.getProcessInstanceId());
		preProcessingResponse.setBusinessKey(instance.getBusinessKey());
		//String processInstanceId = instance.getProcessInstanceId();
	    log.info("Process started with id : {}", instance.getProcessInstanceId());
	    Task task = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult();
	    //String taskId = task.getId();
	    preProcessingResponse.setTaskId(task.getId());
	    preProcessingResponse.setTaskName(task.getName());
	    log.info("Executing task with id {}", task.getId());
	    taskService.complete(task.getId());
	    log.info("Completed the task with id {}", task.getId());
		return preProcessingResponse;
	}

	@Override
	public RuleSet getSpin(RuleSet ruleSet) {
		String json = JSON(ruleSet).toString();
		log.info("json {}", json);
		RuleSet ruleSet1 = JSON(json).mapTo(RuleSet.class);
		log.info("ruleset1 {}", ruleSet1.toString());
		ruleSet1.setId("12345");
		return ruleSet1;
	}

	@Override
	public RuleSet preProcessing(PreProcessingRequest preProcessingRequest) {
		RuleSet response = new RuleSet();
		log.info("In preProcessing method Impl");
		//ProcessInstance instance = runtimeService.startProcessInstanceByKey("approve-loan");
		Map<String, Object> variables = new HashMap<>();
		variables.put("documentKey", preProcessingRequest.getDocumentKey());
		log.info("Starting the instance");
		ProcessInstance instance = 
				runtimeService.startProcessInstanceByKey("pre-processing", variables);
		log.info("Completing intermittent task");
	    taskService.complete(taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId()).singleResult().getId());
				
		response.setName(preProcessingRequest.getDocumentKey() + " : " + instance.getProcessDefinitionId());
		response.setId(instance.getId());
		return response;
	}

}
