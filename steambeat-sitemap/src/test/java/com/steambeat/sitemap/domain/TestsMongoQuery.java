//package com.steambeat.sitemap.domain;
//
//import com.mongodb.FakeDB;
//import com.steambeat.test.SystemTime;
//import fr.bodysplash.mongolink.MongoSession;
//import fr.bodysplash.mongolink.test.FakePersistentContext;
//import org.junit.*;
//
//public class TestsMongoQuery {
//
//    @Rule
//    public FakePersistentContext fakePersistentContext = new FakePersistentContext("com.steambeat.repositories.mapping");
//
//    @Rule
//    public SystemTime time = SystemTime.fixed();
//
//    @Before
//    public void setUp() throws Exception {
//        final MongoSession session = fakePersistentContext.getSession();
//        mongo = (FakeDB) session.getDb();
//    }
//
//    @Test
//    public void canFetchFeeds() {
//        //final Feed before = new Feed("before");
//        //session.save(before);
//        //time.waitDays(1);
//        //DateTime lastBuildDate = time.getNow();
//        //String uri = "uri";
//        //final Feed feed = new Feed(uri);
//        //session.save(feed);
//        //MongoQuery mongoQuery = new MongoQuery(session);
//        //time.waitDays(1);
//        //
//        //List results = mongoQuery.execute(lastBuildDate);
//        //
//        //assertThat(results.size(), is(1));
//        //HashMap foundFeed = (HashMap) results.get(0);
//        //assertThat(foundFeed.get("value").toString(), is("http://www.kikiyoo.com/feeds/" + uri));
//    }
//
//    private FakeDB mongo;
//}
