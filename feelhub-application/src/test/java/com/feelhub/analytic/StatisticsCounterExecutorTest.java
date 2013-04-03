package com.feelhub.analytic;

import com.foursquare.fongo.Fongo;
import com.mongodb.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class StatisticsCounterExecutorTest {

    @Before
    public void setUp() throws Exception {
        db = new Fongo("test").getDB("test");
        executor = new StatisticsCounterExecutor(db);
    }

    @Test
    public void canExecuteUpdate() {
        executor.execute(new StatisticsCounter("collection").withId("date", 1).inc("count"));

        DBObject object = db.getCollection("collection").findOne(new BasicDBObject());
        assertThat(object).isNotNull();
        assertThat(object.get("date")).isEqualTo(1);
        assertThat(object.get("count")).isEqualTo(1);
    }

    @Test
    public void canExecuteMultipleInc() {
        executor.execute(new StatisticsCounter("collection").withId("date", 1).inc("count").inc("other"));

        DBObject object = db.getCollection("collection").findOne(new BasicDBObject());
        assertThat(object).isNotNull();
        assertThat(object.get("count")).isEqualTo(1);
        assertThat(object.get("other")).isEqualTo(1);
    }

    private DB db;
    private StatisticsCounterExecutor executor;
}
