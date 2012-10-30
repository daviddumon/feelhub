package com.feelhub.web.migration;

import com.feelhub.repositories.SessionProvider;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.*;
import org.apache.log4j.Logger;
import org.restlet.Context;

import java.util.List;

public class MigrationRunner implements Runnable {

    @Inject
    public MigrationRunner(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public void setContext(final Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        logger.warn("MIGRATION - RUNNER");
        if (checkUpdateFlag()) {
            final List<Migration> migrations = Lists.newArrayList();
            for (final Migration migration : migrations) {
                sessionProvider.start();
                migration.run();
                sessionProvider.stop();
            }
            removeUpdateFlag();
        } else {
            poll();
        }
        context.getAttributes().replace("com.feelhub.ready", true);
        logger.warn("MIGRATION - RUNNER END");
        Thread.currentThread().interrupt();
        return;
    }

    private boolean checkUpdateFlag() {
        final DBCollection collection = getCollection();
        final DBObject status = getStatus(collection);
        if (status == null) {
            logger.warn("MIGRATION - CHECK UPDATE TRUE");
            lock(collection);
            return true;
        } else {
            logger.warn("MIGRATION - CHECK UPDATE FALSE");
            return false;
        }
    }

    private DBCollection getCollection() {
        final DB db = sessionProvider.get().getDb();
        return db.getCollection("status");
    }

    private DBObject getStatus(final DBCollection collection) {
        try {
            return collection.findOne();
        } catch (Exception e) {
            return null;
        }
    }

    private void lock(final DBCollection collection) {
        final BasicDBObject query = new BasicDBObject();
        query.put("value", "update");
        collection.insert(query);
    }

    private void poll() {
        logger.warn("MIGRATION - POLLING");
        final DBCollection collection = getCollection();
        DBObject status = getStatus(collection);
        while (status != null) {
            logger.warn("MIGRATION - POLLING WAIT");
            status = getStatus(collection);
            try {
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.warn("MIGRATION - POLLING END");
    }

    private void removeUpdateFlag() {
        final DB db = sessionProvider.get().getDb();
        final DBCollection collection = db.getCollection("status");
        collection.drop();
        logger.warn("MIGRATION - REMOVE FLAG UPDATE");
    }

    protected Context context;
    private SessionProvider sessionProvider;
    private final int ONE_SECOND = 1000;
    private static Logger logger = Logger.getLogger(MigrationRunner.class);
}
