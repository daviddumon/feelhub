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
            doRun();
            endOfMigration();
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
        logger.warn("MIGRATION CAN RUN : " + results.size() + " - " + number);
        return results.size() < number;
    }

    abstract protected void doRun();

    private void endOfMigration() {
        final DB db = provider.get().getDb();
        final DBCollection migrationCollection = db.getCollection("migration");
        final BasicDBObject query = new BasicDBObject();
        query.put("number", number);
        query.put("creationDate", new DateTime().getMillis());
        migrationCollection.insert(query);
        logger.warn("MIGRATION END OF MIGRATION INSERT");
    }

    protected SessionProvider provider;
    protected int number;
    protected static Logger logger = Logger.getLogger(Migration.class);
}
