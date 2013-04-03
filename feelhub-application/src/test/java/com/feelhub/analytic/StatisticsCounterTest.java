package com.feelhub.analytic;

import com.mongodb.DBObject;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class StatisticsCounterTest {

    @Test
    public void canBuildUpdateWithInc() {
        final StatisticsCounter counter = new StatisticsCounter("test").inc("test").inc("autre");

        final DBObject object = counter.object();

        assertThat(object).isNotNull();
        assertThat(object.containsField("$inc")).isTrue();
        final DBObject incs = (DBObject) object.get("$inc");
        assertThat(incs.get("test")).isEqualTo(1);
        assertThat(incs.get("autre")).isEqualTo(1);
    }

    @Test
    public void canBuildUpdateWithSet() {
        final StatisticsCounter counter = new StatisticsCounter("test").set("test", 2);

        final DBObject object = counter.object();

        assertThat(object).isNotNull();
        assertThat(object.containsField("$set")).isTrue();
        final DBObject set = (DBObject) object.get("$set");
        assertThat(set.get("test")).isEqualTo(2);
    }
}
