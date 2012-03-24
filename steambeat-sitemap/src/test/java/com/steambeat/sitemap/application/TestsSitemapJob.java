package com.steambeat.sitemap.application;

import com.mongodb.FakeDB;
import com.steambeat.test.SystemTime;
import org.junit.*;
import org.mongolink.MongoSession;
import org.mongolink.test.FakePersistentContext;
import org.quartz.JobExecutionException;

public class TestsSitemapJob {

    @Rule
    public FakePersistentContext fakePersistentContext = new FakePersistentContext("com.steambeat.repositories.mapping");

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() throws Exception {
        final MongoSession session = fakePersistentContext.getSession();
        mongo = (FakeDB) session.getDb();
    }

    @Test
    public void canSetLastBuildDate() throws JobExecutionException {
    //    SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
    //    MongoQuery mongoQuery = mock(MongoQuery.class);
    //    when(mongoQuery.execute(null)).thenReturn(new ArrayList());
    //    SitemapJob sitemapJob = new SitemapJob(sitemapBuilder, mongoQuery);
    //
    //    sitemapJob.execute(null);
    //
    //    assertThat(sitemapJob.getLastBuildDate(), notNullValue());
    //    assertThat(sitemapJob.getLastBuildDate(), is(time.getNow()));
    }

    //@Test
    //public void lastBuildDateIsStatic() throws JobExecutionException {
    //    SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
    //    MongoQuery mongoQuery = mock(MongoQuery.class);
    //    when(mongoQuery.execute(null)).thenReturn(new ArrayList());
    //    SitemapJob sitemapJob = new SitemapJob(sitemapBuilder, mongoQuery);
    //    sitemapJob.execute(null);
    //
    //    SitemapJob sitemapJob2 = new SitemapJob(sitemapBuilder, new MongoQuery(null));
    //
    //    assertThat(sitemapJob.getLastBuildDate(), is(sitemapJob2.getLastBuildDate()));
    //}
    //
    //@Test
    //public void canExecute() throws JobExecutionException {
    //    SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
    //    MongoQuery mongoQuery = mock(MongoQuery.class);
    //    when(mongoQuery.execute(null)).thenReturn(new ArrayList());
    //    SitemapJob sitemapJob = new SitemapJob(sitemapBuilder, mongoQuery);
    //
    //    sitemapJob.execute(null);
    //
    //    verify(sitemapBuilder, times(1)).build(0, Lists.newArrayList());
    //}
    //
    //@SuppressWarnings("unchecked")
    //@Test
    //public void canIncreaseLastUrisCount() throws JobExecutionException {
    //    List uris = Lists.newArrayList();
    //    Map uri = new HashMap();
    //    uri.put("value", "http://www.test.com");
    //    uri.put("frequence", Frequency.hourly);
    //    uris.add(uri);
    //    SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
    //    MongoQuery mongoQuery = mock(MongoQuery.class);
    //    SitemapJob sitemapJob = new SitemapJob(sitemapBuilder, mongoQuery);
    //    when(mongoQuery.execute(sitemapJob.getLastBuildDate())).thenReturn(uris);
    //
    //    sitemapJob.execute(null);
    //
    //    assertThat(sitemapJob.getLastUrisCount(), is(1));
    //}
    //
    //@SuppressWarnings("unchecked")
    //@Test
    //public void lastUrisCountIsStatic() throws JobExecutionException {
    //    List uris = Lists.newArrayList();
    //    Map uri = new HashMap();
    //    uri.put("value", "http://www.test.com");
    //    uri.put("frequence", Frequency.hourly);
    //    uris.add(uri);
    //    SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
    //    MongoQuery mongoQuery = mock(MongoQuery.class);
    //    SitemapJob sitemapJob = new SitemapJob(sitemapBuilder, mongoQuery);
    //    when(mongoQuery.execute(sitemapJob.getLastBuildDate())).thenReturn(uris);
    //    sitemapJob.setLastUrisCount(0);
    //    sitemapJob.execute(null);
    //
    //    SitemapJob secondJob = new SitemapJob();
    //
    //    assertThat(secondJob.getLastUrisCount(), is(1));
    //}

    private FakeDB mongo;
}
