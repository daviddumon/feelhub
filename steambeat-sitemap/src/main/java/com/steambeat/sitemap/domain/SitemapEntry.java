package com.steambeat.sitemap.domain;

import org.joda.time.DateTime;

public class SitemapEntry {

    public SitemapEntry(final String loc, final Frequency frequency, final double priority) {
        this.loc = loc;
        this.frequency = frequency;
        lastMod = new DateTime();
        this.priority = priority;
    }

    public String getLoc() {
        return loc;
    }

    public DateTime getLastMod() {
        return lastMod;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public double getPriority() {
        return priority;
    }

    public void setLastMod(final DateTime dateTime) {
        this.lastMod = dateTime;
    }

    private final String loc;
    private DateTime lastMod;
    private final Frequency frequency;
    private final double priority;
}
