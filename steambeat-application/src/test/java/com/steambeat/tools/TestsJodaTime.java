package com.steambeat.tools;

import org.joda.time.*;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsJodaTime {

    @Test
    public void canCeil() {
        final DateTime dateTime = new DateTime();

        final DateTime ceil = dateTime.hourOfDay().roundCeilingCopy();

        assertThat(ceil.getMinuteOfHour(), is(0));
        assertThat(ceil.getMillisOfSecond(), is(0));
    }

    @Test
    public void canFloor() {
        final DateTime dateTime = new DateTime();

        final DateTime floor = dateTime.hourOfDay().roundFloorCopy();

        assertThat(floor.getMinuteOfHour(), is(0));
        assertThat(floor.getMillisOfSecond(), is(0));
    }

    @Test
    public void testInterval() {
        final DateTime dateTime = new DateTime();
        final DateTime ceil = dateTime.hourOfDay().roundCeilingCopy();
        final DateTime floor = dateTime.hourOfDay().roundFloorCopy();
        final Interval interval = new Interval(floor, ceil);

        assertThat(interval.contains(floor), is(true));
        assertThat(interval.contains(ceil), is(false));
        assertThat(interval, is(dateTime.hourOfDay().toInterval()));
    }
}
