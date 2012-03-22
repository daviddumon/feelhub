package com.steambeat.web.migration;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.*;
import com.steambeat.repositories.SessionProvider;
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
        logger.warn("MIGRATION RUNNER RUN");
        if (checkUpdateFlag()) {
            List<Migration> migrations = Lists.newArrayList();
            migrations.add(new Migration00001(sessionProvider));
            migrations.add(new Migration00002(sessionProvider));
            for (Migration migration : migrations) {
                sessionProvider.start();
                migration.run();
                sessionProvider.stop();
            }
            removeUpdateFlag();
        } else {
            poll();
        }
        context.getAttributes().replace("com.steambeat.ready", true);
        Thread.currentThread().interrupt();
        return;
    }

    private boolean checkUpdateFlag() {
        final DBCollection collection = getCollection();
        DBObject status = getStatus(collection);
        if (status == null) {
            logger.warn("CHECK UPDATE TRUE");
            lock(collection);
            return true;
        } else {
            logger.warn("CHECK UPDATE FALSE");
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
        logger.warn("POLLING STATUS");
        final DBCollection collection = getCollection();
        DBObject status = getStatus(collection);
        while (status != null) {
            logger.warn("STATUS NULL");
            status = getStatus(collection);
            try {
                Thread.sleep(ONE_MINUTE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void removeUpdateFlag() {
        final DB db = sessionProvider.get().getDb();
        final DBCollection collection = db.getCollection("status");
        collection.drop();
    }

    protected Context context;
    private SessionProvider sessionProvider;
    private final int ONE_MINUTE = 60000;
    private static Logger logger = Logger.getLogger(MigrationRunner.class);
}
