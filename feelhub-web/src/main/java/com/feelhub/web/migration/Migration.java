package com.feelhub.web.migration;

import com.feelhub.repositories.SessionProvider;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.mongodb.*;
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
        logger.warn("MIGRATION - MIGRATION " + number);
        if (canRun()) {
            doRun();
            endOfMigration();
        }
        logger.warn("MIGRATION - END OF MIGRATION " + number);
    }

    protected boolean canRun() {
        final DB db = provider.get().getDb();
        final DBCollection migrationCollection = db.getCollection("migration");
        final BasicDBObject query = new BasicDBObject();
        query.put("number", number);
        final DBCursor cursor = migrationCollection.find(query);
        final List<Object> results = Lists.newArrayList();
        while (cursor.hasNext()) {
            results.add(cursor.next());
        }
        cursor.close();
        logger.warn("MIGRATION CAN RUN : " + results.isEmpty());
        return results.isEmpty();
    }

    abstract protected void doRun();

    protected void endOfMigration() {
        final DB db = provider.get().getDb();
        final DBCollection migrationCollection = db.getCollection("migration");
        final BasicDBObject query = new BasicDBObject();
        query.put("number", number);
        query.put("creationDate", new DateTime().getMillis());
        migrationCollection.insert(query);
        logger.warn("MIGRATION - INSERT IN MIGRATION COLLECTION");
    }

    protected SessionProvider provider;
    protected int number;
    protected static Logger logger = Logger.getLogger(Migration.class);
}