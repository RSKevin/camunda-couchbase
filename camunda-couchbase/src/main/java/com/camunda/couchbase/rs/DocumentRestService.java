package com.camunda.couchbase.rs;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.camunda.couchbase.model.AppResponse;

@RequestMapping("/api/document")
public interface DocumentRestService {
	
	@GetMapping("/key/{key}")
	public AppResponse getByKey(@PathVariable String key);

	@GetMapping("/type/{type}")
	public AppResponse getByType(@PathVariable String type);
	
	@PostMapping("/upsert")
	public AppResponse upsertDoc(@RequestBody Object payload);

}
