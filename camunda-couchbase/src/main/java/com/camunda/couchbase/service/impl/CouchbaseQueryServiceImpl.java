package com.camunda.couchbase.service.impl;

import com.camunda.couchbase.service.client.CouchbaseClientService;
import com.camunda.couchbase.service.CouchbaseQueryService;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.couchbase.client.java.query.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.*;
import static org.camunda.spin.Spin.JSON;


@Service
public class CouchbaseQueryServiceImpl implements CouchbaseQueryService {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    CouchbaseClientService couchbaseClientService;

    @Override
    public Map<String, Object> getByKey(String key) {
        N1qlQueryResult result = null;
        Map<String, Object> responseData = null;
        try {
            Bucket bucket = couchbaseClientService.getCouchbaseBucket();
            if (bucket != null) {
                log.info("Calling query service");
                /*Statement statement = select(x("meta().id, equitypm.*"))
                    .from(i(bucket.name())).as("equitypm").useKeys(key);
                result = bucket.query(N1qlQuery.simple(statement));*/
                result = bucket.query(N1qlQuery.simple("SELECT meta().id, equitypm.* FROM `" + bucket.name() + "` equitypm USE KEYS '" + key + "'"));
                couchbaseClientService.closeBucket(bucket);
            }
        } catch (Exception e) {
            log.error("Unable to get couchbase bucket via ClientService: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        /*JsonDocument jsonDocument = bucket.get(key);
        Map<String, Object> responseData = null;
         if (jsonDocument != null)
             responseData = jsonDocument.content().toMap();*/

        if (result != null && !result.allRows().isEmpty())
            responseData = result.allRows().get(0).value().toMap();

        return responseData;
    }

    @Override
    public List<Map<String, Object>> getByType(String type) {
        N1qlQueryResult result = null;
        List<Map<String, Object>> responseData = null;
        try {
            Bucket bucket = couchbaseClientService.getCouchbaseBucket();
            if (bucket != null) {
                log.info("Calling query service");
                //Statement statement = select(x("meta().id, name, type, rules, _audit, pmGroup"))
                Statement statement = select(x("meta().id, equitypm.*"))
                        .from(i(bucket.name())).as("equitypm")
                        .where( x("equitypm.type").eq(s(type)));
                result = bucket.query(N1qlQuery.simple(statement));
                couchbaseClientService.closeBucket(bucket);
            }
        } catch (Exception e) {
            log.error("Unable to get couchbase bucket via ClientService: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

        /*responseData.put("status", result.status());
        responseData.put("rows", result.allRows()
                .stream()
                .map(i -> i.value().toMap())
                .collect(Collectors.toList()));*/
        if (result != null) {
            responseData = result.allRows()
                    .stream()
                    .map(i -> i.value().toMap())
                    .collect(Collectors.toList());
        }
        return responseData;
    }

	@Override
	public String upsertDoc(Map<String, Object> mapObject) {
		String json = JSON(mapObject).toString();
		log.info("In insertDoc method {}", json);
		@SuppressWarnings("unused")
		JsonDocument inserted = null;
		try {
            Bucket bucket = couchbaseClientService.getCouchbaseBucket();
            if (bucket != null) {
                log.info("Calling query service");
                //JsonObject content = JsonObject.create();
                //mapObject.forEach((key, value) -> content.put(key, value));
                JsonObject content = JsonObject.create().put("data", json);
                //JsonObject content = new JsonObject(mapObject);
                inserted = bucket.upsert(JsonDocument.create("preProcessingTest::1", content));
                couchbaseClientService.closeBucket(bucket);
            }
        } catch (Exception e) {
            log.error("Unable to get couchbase bucket via ClientService: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
		return "Successfully Inserted!";
	}
    
    

    /*public Map<String, Object> getByType(String type) {
        Bucket bucket = null;
        try {
            bucket = couchbaseClientService.getCouchbaseBucket();
        } catch (Exception e) {
            log.error("Unable to get couchbase bucket via ClientService: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

		if (bucket != null) {
			JsonObject content = JsonObject.create().put("hello", "world");
			JsonDocument inserted = bucket.upsert(JsonDocument.create("helloworld", content));
		}

        log.info("Before calling query service");
        //N1qlQueryResult result = bucket.query(N1qlQuery.simple("SELECT meta().id, name, type, rules, _audit, pmGroup FROM `" + bucket.name() + "` WHERE type = 'ruleSetTest'"));

        //String statement1 = "SELECT meta().id, name, type, rules, _audit, pmGroup FROM `" + bucket.name() + "` WHERE type = '" + type +"'";

        Statement statement = select(x("meta().id, name, type, rules, _audit, pmGroup"))
                .from(i(bucket.name()))
                .where( x("type").eq(s(type)));
        N1qlQueryResult result = bucket.query(N1qlQuery.simple(statement));

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", result.status());
        responseData.put("rows", result.allRows()
                .stream()
                .map(i -> i.value().toMap())
                .collect(Collectors.toList()));

        log.info("After calling query service {}", responseData);
        return responseData;
    }*/
}