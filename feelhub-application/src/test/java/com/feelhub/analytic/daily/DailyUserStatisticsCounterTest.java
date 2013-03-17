package com.feelhub.analytic.daily;

import com.google.common.collect.Iterables;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.fest.assertions.Assertions.*;

public class DailyUserStatisticsCounterTest {

    @Before
    public void setUp() throws Exception {
        db = new MongoClient("localhost").getDB("test");
    }

    @After
    public void tearDown() throws Exception {
        db.dropDatabase();
    }

    @Test
    @Ignore
    public void integrationTest() throws UnknownHostException {
        Jongo jongo = new Jongo(db);
        DailyUserStatisticsCounter stat = new DailyUserStatisticsCounter(jongo);

        stat.incrementActivationCount();

        Iterable<DailyUserStatistic> dailystat = jongo.getCollection("dailyuserstatistic").find().as(DailyUserStatistic.class);
        DailyUserStatistic dailyUserStatistic = Iterables.get(dailystat, 0);
        assertThat(dailyUserStatistic.activations).isEqualTo(1);
        assertThat(dailyUserStatistic.creations).isEqualTo(0);
    }

    private DB db;
}
