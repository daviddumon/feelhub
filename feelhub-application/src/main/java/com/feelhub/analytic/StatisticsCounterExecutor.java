package com.feelhub.analytic;

import com.mongodb.*;

import javax.inject.Inject;

public class StatisticsCounterExecutor {

    @Inject
    public StatisticsCounterExecutor(final DB db) {
        this.db = db;
    }

    public void execute(final StatisticsCounter counter) {
        final DBCollection collection = db.getCollection(counter.getCollectionName());
        collection.ensureIndex(String.format("{%s:1}", counter.getIdField()));
        collection.update(counter.query(), counter.object(), true, false);
    }

    private final DB db;
}
