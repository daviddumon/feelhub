package com.feelhub.analytic;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.List;
import java.util.Map;

public class StatisticsCounter {

    public StatisticsCounter(String collectionName) {
        this.collectionName = collectionName;
    }

    public StatisticsCounter withId(String idField, Object idValue) {
        this.idField = idField;
        this.idValue = idValue;
        return this;
    }

    public StatisticsCounter inc(String field) {
        incs.add(field);
        return this;
    }


    public String getCollectionName() {
        return collectionName;
    }

    public Object getIdValue() {
        return idValue;
    }

    public String getIdField() {
        return idField;
    }

    public StatisticsCounter set(String key, Object value) {
        sets.put(key, value);
        return this;
    }

    public DBObject object() {
        BasicDBObject result = new BasicDBObject();
        fillInc(result);
        fillSet(result);
        return result;
    }

    private void fillSet(BasicDBObject result) {
        if(sets.isEmpty()) {
            return;
        }
        BasicDBObject modifier = new BasicDBObject();
        modifier.putAll(sets);
        result.put("$set", modifier);
    }

    private void fillInc(BasicDBObject result) {
        if(incs.isEmpty()) {
            return;
        }
        BasicDBObject modifier = new BasicDBObject();
        for (String inc : this.incs) {
            modifier.put(inc, 1);
        }
        result.put("$inc", modifier);
    }

    public DBObject query() {
        BasicDBObject result = new BasicDBObject();
        result.put(idField, idValue);
        return result;
    }

    public boolean hasInc(String key) {
        return incs.contains(key);
    }

    public boolean hasSet(String key, Object value) {
        return value.equals(sets.get(key));
    }

    private List<String> incs = Lists.newArrayList();
    private Map<String, Object> sets = Maps.newConcurrentMap();
    private String idField;
    private Object idValue;
    private String collectionName;
}
