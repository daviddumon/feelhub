package com.bytedojo.domain;

import com.bytedojo.test.SystemTime;
import com.bytedojo.tools.HiramProperties;
import com.google.common.collect.Lists;
import fr.bodysplash.mongolink.*;
import fr.bodysplash.mongolink.domain.mapper.ContextBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.quartz.JobExecutionException;

import java.io.File;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestsHiramJob {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void setUp() throws Exception {
        //session = fakePersistentContext.getSession();
        final HiramProperties hiramProperties = new HiramProperties();
        ContextBuilder contextBuilder = new ContextBuilder("com.bytedojo.context");
        mongoSessionManager = MongoSessionManager.create(contextBuilder, hiramProperties.getDbSettings());
        session = mongoSessionManager.createSession();
    }

    @After
    public void tearDown() {
        session.getDb().dropDatabase();
        mongoSessionManager.close();
        FileUtils.deleteQuietly(new File("hiram.log"));
    }

    @Test
    public void canSetLastBuildDate() throws JobExecutionException {
        SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
        MongoQuery mongoQuery = mock(MongoQuery.class);
        when(mongoQuery.execute(null)).thenReturn(new ArrayList());
        HiramJob hiramJob = new HiramJob(sitemapBuilder, mongoQuery);

        hiramJob.execute(null);

        assertThat(hiramJob.getLastBuildDate(), notNullValue());
        assertThat(hiramJob.getLastBuildDate(), is(time.getNow()));
    }

    @Test
    public void lastBuildDateIsStatic() throws JobExecutionException {
        SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
        MongoQuery mongoQuery = mock(MongoQuery.class);
        when(mongoQuery.execute(null)).thenReturn(new ArrayList());
        HiramJob hiramJob = new HiramJob(sitemapBuilder, mongoQuery);
        hiramJob.execute(null);

        HiramJob hiramJob2 = new HiramJob(sitemapBuilder, new MongoQuery(null));

        assertThat(hiramJob.getLastBuildDate(), is(hiramJob2.getLastBuildDate()));
    }

    @Test
    public void canExecute() throws JobExecutionException {
        SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
        MongoQuery mongoQuery = mock(MongoQuery.class);
        when(mongoQuery.execute(null)).thenReturn(new ArrayList());
        HiramJob hiramJob = new HiramJob(sitemapBuilder, mongoQuery);

        hiramJob.execute(null);

        verify(sitemapBuilder, times(1)).build(0, Lists.newArrayList());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void canIncreaseLastUrisCount() throws JobExecutionException {
        List uris = Lists.newArrayList();
        Map uri = new HashMap();
        uri.put("value", "http://www.test.com");
        uri.put("frequence", Frequence.hourly);
        uris.add(uri);
        SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
        MongoQuery mongoQuery = mock(MongoQuery.class);
        HiramJob hiramJob = new HiramJob(sitemapBuilder, mongoQuery);
        when(mongoQuery.execute(hiramJob.getLastBuildDate())).thenReturn(uris);

        hiramJob.execute(null);

        assertThat(hiramJob.getLastUrisCount(), is(1));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void lastUrisCountIsStatic() throws JobExecutionException {
        List uris = Lists.newArrayList();
        Map uri = new HashMap();
        uri.put("value", "http://www.test.com");
        uri.put("frequence", Frequence.hourly);
        uris.add(uri);
        SitemapBuilder sitemapBuilder = mock(SitemapBuilder.class);
        MongoQuery mongoQuery = mock(MongoQuery.class);
        HiramJob hiramJob = new HiramJob(sitemapBuilder, mongoQuery);
        when(mongoQuery.execute(hiramJob.getLastBuildDate())).thenReturn(uris);
        hiramJob.setLastUrisCount(0);
        hiramJob.execute(null);

        HiramJob secondJob = new HiramJob();

        assertThat(secondJob.getLastUrisCount(), is(1));
    }

    private MongoSession session;
    private MongoSessionManager mongoSessionManager;
}
