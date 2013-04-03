package com.feelhub.analytic;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.List;
import java.util.Map;

public class StatisticsCounter {

    public StatisticsCounter(final String collectionName) {
        this.collectionName = collectionName;
    }

    public StatisticsCounter withId(final String idField, final Object idValue) {
        this.idField = idField;
        this.idValue = idValue;
        return this;
    }

    public StatisticsCounter inc(final String field) {
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

    public StatisticsCounter set(final String key, final Object value) {
        sets.put(key, value);
        return this;
    }

    public DBObject object() {
        final BasicDBObject result = new BasicDBObject();
        fillInc(result);
        fillSet(result);
        return result;
    }

    private void fillSet(final BasicDBObject result) {
        if(sets.isEmpty()) {
            return;
        }
        final BasicDBObject modifier = new BasicDBObject();
        modifier.putAll(sets);
        result.put("$set", modifier);
    }

    private void fillInc(final BasicDBObject result) {
        if(incs.isEmpty()) {
            return;
        }
        final BasicDBObject modifier = new BasicDBObject();
        for (final String inc : this.incs) {
            modifier.put(inc, 1);
        }
        result.put("$inc", modifier);
    }

    public DBObject query() {
        final BasicDBObject result = new BasicDBObject();
        result.put(idField, idValue);
        return result;
    }

    public boolean hasInc(final String key) {
        return incs.contains(key);
    }

    public boolean hasSet(final String key, final Object value) {
        return value.equals(sets.get(key));
    }

    private final List<String> incs = Lists.newArrayList();
    private final Map<String, Object> sets = Maps.newConcurrentMap();
    private String idField;
    private Object idValue;
    private final String collectionName;
}
