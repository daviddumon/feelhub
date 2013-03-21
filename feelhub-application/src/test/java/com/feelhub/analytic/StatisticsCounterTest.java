package com.feelhub.analytic;

import com.mongodb.DBObject;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class StatisticsCounterTest {

    @Test
    public void canBuildUpdateWithInc() {
        StatisticsCounter counter = new StatisticsCounter("test").inc("test").inc("autre");

        DBObject object = counter.object();

        assertThat(object).isNotNull();
        assertThat(object.containsField("$inc")).isTrue();
        DBObject incs = (DBObject) object.get("$inc");
        assertThat(incs.get("test")).isEqualTo(1);
        assertThat(incs.get("autre")).isEqualTo(1);
    }

    @Test
    public void canBuildUpdateWithSet() {
        StatisticsCounter counter = new StatisticsCounter("test").set("test", 2);

        DBObject object = counter.object();

        assertThat(object).isNotNull();
        assertThat(object.containsField("$set")).isTrue();
        DBObject set = (DBObject) object.get("$set");
        assertThat(set.get("test")).isEqualTo(2);
    }
}
