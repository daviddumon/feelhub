package com.feelhub.domain.admin;

import com.feelhub.domain.BaseEntity;

import java.util.UUID;

public class AdminStatistic extends BaseEntity {

    protected AdminStatistic() {
        // mongolink
    }

    public AdminStatistic(final String month, final Api api) {
        this.month = month;
        this.api = api;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getMonth() {
        return month;
    }

    public int getCount() {
        return count;
    }

    public Api getApi() {
        return api;
    }

    public void increment(final int increment) {
        count += increment;
    }

    private final UUID id = UUID.randomUUID();
    private String month;
    private int count;
    private Api api;

}
