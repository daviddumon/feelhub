package com.steambeat.web.migration;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.*;
import com.steambeat.repositories.SessionProvider;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.List;

public abstract class Migration {

    @Inject
    public Migration(final SessionProvider provider, final int number) {
        this.provider = provider;
        this.number = number;
    }

    public void run() {
        if (canRun()) {
            try {
                if (notRunned()) {
                    doRun();
                    endOfMigration();
                } else {
                    poll();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean notRunned() {
        final DBCollection collection = getCollection();
        DBObject status = getStatus(collection);
        if (status == null) {
            lock(collection);
            return true;
        } else {
            return false;
        }
    }

    private void poll() throws InterruptedException {
        final DBCollection collection = getCollection();
        DBObject status = getStatus(collection);
        while (status == null) {
            status = getStatus(collection);
            Thread.sleep(TEN_SECONDS);
        }
    }

    private void lock(final DBCollection collection) {
        final BasicDBObject query = new BasicDBObject();
        query.put("value", "update");
        collection.insert(query);
    }

    private DBCollection getCollection() {
        final DB db = provider.get().getDb();
        return db.getCollection("status");
    }

    private DBObject getStatus(final DBCollection collection) {
        try {
            return collection.findOne();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean canRun() {
        final DB db = provider.get().getDb();
        final DBCollection migrationCollection = db.getCollection("migration");
        final BasicDBObject query = new BasicDBObject();
        query.put("number", number);
        final DBCursor cursor = migrationCollection.find(query);
        List<Object> results = Lists.newArrayList();
        while (cursor.hasNext()) {
            results.add(cursor.next());
        }
        cursor.close();
        return results.size() < number;
    }

    abstract protected void doRun();

    private void endOfMigration() {
        removeUpdateFlag();
        final DB db = provider.get().getDb();
        final DBCollection migrationCollection = db.getCollection("migration");
        final BasicDBObject query = new BasicDBObject();
        query.put("number", number);
        query.put("creationDate", new DateTime().getMillis());
        migrationCollection.insert(query);
    }

    private void removeUpdateFlag() {
        final DB db = provider.get().getDb();
        final DBCollection collection = db.getCollection("status");
        collection.drop();
    }

    protected SessionProvider provider;
    protected int number;
    private final int TEN_SECONDS = 10000;
    protected static Logger logger = Logger.getLogger(Migration.class);
}
