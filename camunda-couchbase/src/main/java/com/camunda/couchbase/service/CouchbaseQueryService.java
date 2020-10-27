package com.camunda.couchbase.service;

import java.util.List;
import java.util.Map;

public interface CouchbaseQueryService {
    public Map<String, Object> getByKey(String key);
    public List<Map<String, Object>> getByType(String type);
    public String upsertDoc(Map<String, Object> mapObject);
}
