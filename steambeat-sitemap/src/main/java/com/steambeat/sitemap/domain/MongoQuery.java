//package com.steambeat.sitemap.domain;
//
//import com.google.common.collect.Lists;
//import com.mongodb.*;
//import fr.bodysplash.mongolink.MongoSession;
//import org.joda.time.DateTime;
//
//import java.util.*;
//
//@SuppressWarnings("unchecked")
//public class MongoQuery {
//
//    public MongoQuery(final MongoSession mongoSession) {
//        this.mongoSession = mongoSession;
//    }
//
//    public List execute(final DateTime lastBuildDate) {
//        mongoSession.start();
//        BasicDBObject query = new BasicDBObject();
//        BasicDBObject criteria = new BasicDBObject();
//        criteria.put("$gte", lastBuildDate.getMillis());
//        query.put("creationDate", criteria);
//        DB db = mongoSession.getDb();
//        List results = Lists.newArrayList();
//        DBCollection feed = db.getCollection("feed");
//        DBCursor cursor = feed.find(query);
//        while (cursor.hasNext()) {
//            final DBObject next = cursor.next();
//            Map uri = new HashMap();
//            uri.put("value", "http://www.steambeat.com/webpages/" + next.get("_id"));
//            uri.put("frequence", Frequency.hourly);
//            uri.put("priority", 0.5);
//            results.add(uri);
//        }
//        mongoSession.stop();
//        return results;
//    }
//
//    private final MongoSession mongoSession;
//}
