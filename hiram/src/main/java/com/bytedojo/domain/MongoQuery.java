package com.bytedojo.domain;

import com.google.common.collect.Lists;
import com.mongodb.*;
import fr.bodysplash.mongolink.MongoSession;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.util.*;

public class MongoQuery {

    public MongoQuery(final MongoSession mongoSession) {
        this.mongoSession = mongoSession;
    }

    public List execute(final DateTime lastBuildDate) {
        logger.info("Execution de la query");
        mongoSession.start();
        BasicDBObject query = new BasicDBObject();
        BasicDBObject criteria = new BasicDBObject();
        criteria.put("$gte", lastBuildDate.getMillis());
        query.put("creationDate", criteria);
        DB db = mongoSession.getDb();
        List results = Lists.newArrayList();
        DBCollection feed = db.getCollection("feed");
        DBCursor cursor = feed.find(query);
        while (cursor.hasNext()) {
            final DBObject next = cursor.next();
            Map uri = new HashMap();
            uri.put("value", "http://www.kikiyoo.com/feeds/" + next.get("_id"));
            uri.put("frequence", Frequence.hourly);
            uri.put("priority", 0.5);
            results.add(uri);
        }
        mongoSession.stop();
        logger.info("Fin de la query");
        return results;
    }

    private final MongoSession mongoSession;
    private static final Logger logger = Logger.getLogger(MongoQuery.class);
}
