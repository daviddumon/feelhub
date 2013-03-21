package com.feelhub.analytic;

import com.mongodb.DB;
import com.mongodb.DBCollection;

import javax.inject.Inject;

public class StatisticsCounterExecutor {

    @Inject
    public StatisticsCounterExecutor(DB db) {
        this.db = db;
    }

    public void execute(StatisticsCounter counter) {
        DBCollection collection = db.getCollection(counter.getCollectionName());
        collection.ensureIndex(String.format("{%s:1}", counter.getIdField()));
        collection.update(counter.query(), counter.object(), true, false);
    }

    private DB db;
}
