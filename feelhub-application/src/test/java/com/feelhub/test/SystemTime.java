package com.feelhub.test;

import org.joda.time.*;
import org.junit.rules.ExternalResource;

public class SystemTime extends ExternalResource {

    public static SystemTime fixed() {
        return new SystemTime(new DateTime());
    }

    private SystemTime(final DateTime dateTime) {
        now = dateTime;
    }

    @Override
    protected void before() throws Throwable {
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());
    }

    @Override
    protected void after() {
        DateTimeUtils.setCurrentMillisSystem();
    }

    public void waitYears(final int years) {
        set(now.plusYears(years));
    }

    public void waitMonths(final int months) {
        set(now.plusMonths(months));
    }

    public void waitDays(final int days) {
        set(now.plusDays(days));
    }

    public void waitHours(final int hours) {
        set(now.plusHours(hours));
    }

    public void waitMinutes(final int minutes) {
        set(now.plusMinutes(minutes));
    }

    public void removeDays(final int days) {
        set(now.minusDays(days));
    }

    public DateTime getNow() {
        return now;
    }

    public void set(final DateTime time) {
        now = time;
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());
    }

    private DateTime now;
}
