package com.bytedojo.domain;

import com.bytedojo.test.SystemTime;
import com.bytedojo.tools.HiramProperties;
import fr.bodysplash.mongolink.*;
import fr.bodysplash.mongolink.domain.mapper.ContextBuilder;
import org.junit.*;

public class TestsMongoQuery {

    //@Rule
    //public FakePersistentContext fakePersistentContext = new FakePersistentContext("com.bytedojo.context.mapping");

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() throws Exception {
        //session = fakePersistentContext.getSession();
        final HiramProperties hiramProperties = new HiramProperties();
        ContextBuilder contextBuilder = new ContextBuilder("com.kikiyoo.repositories.context");
        mongoSessionManager = MongoSessionManager.create(contextBuilder, hiramProperties.getDbSettings());
        session = mongoSessionManager.createSession();
    }

    @After
    public void tearDown() {
        session.getDb().dropDatabase();
        mongoSessionManager.close();
    }

    @Test
    public void canFetchFeeds() {
        //final Feed before = new Feed("before");
        //session.save(before);
        //time.waitDays(1);
        //DateTime lastBuildDate = time.getNow();
        //String uri = "uri";
        //final Feed feed = new Feed(uri);
        //session.save(feed);
        //MongoQuery mongoQuery = new MongoQuery(session);
        //time.waitDays(1);
        //
        //List results = mongoQuery.execute(lastBuildDate);
        //
        //assertThat(results.size(), is(1));
        //HashMap foundFeed = (HashMap) results.get(0);
        //assertThat(foundFeed.get("value").toString(), is("http://www.kikiyoo.com/feeds/" + uri));
    }

    private MongoSession session;
    private MongoSessionManager mongoSessionManager;
}
