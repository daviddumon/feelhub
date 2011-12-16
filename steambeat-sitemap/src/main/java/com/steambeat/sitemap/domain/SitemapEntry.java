package com.steambeat.sitemap.domain;

import org.joda.time.DateTime;

public class SitemapEntry {

    public SitemapEntry(final String loc, final Frequency frequency, final double priority) {
        this.loc = loc;
        this.frequency = Frequency.hourly;
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

    private final String loc;
    private final DateTime lastMod;
    private final Frequency frequency;
    private final double priority;
}
