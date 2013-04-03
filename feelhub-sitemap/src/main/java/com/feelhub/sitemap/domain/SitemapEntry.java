package com.feelhub.sitemap.domain;

import com.feelhub.domain.BaseEntity;
import org.joda.time.DateTime;

public class SitemapEntry extends BaseEntity {

    public SitemapEntry(final String loc, final Frequency frequency, final double priority, final DateTime lastModificationDate) {
        this.loc = loc;
        this.frequency = frequency;
        lastMod = lastModificationDate;
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

    @Override
    public Object getId() {
        return loc;
    }

    private final String loc;
    private final DateTime lastMod;
    private final Frequency frequency;
    private final double priority;
}
