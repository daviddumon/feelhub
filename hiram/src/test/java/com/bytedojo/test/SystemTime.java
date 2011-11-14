package com.bytedojo.test;

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

    public void waitDays(final int days) {
        now = now.plusDays(days);
        DateTimeUtils.setCurrentMillisFixed(now.getMillis());
    }

    public DateTime getNow() {
        return now;
    }

    private DateTime now;
}
