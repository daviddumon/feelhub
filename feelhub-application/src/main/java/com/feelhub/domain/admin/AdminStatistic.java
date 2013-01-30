package com.feelhub.domain.admin;

import com.feelhub.domain.BaseEntity;

import java.util.UUID;

public class AdminStatistic extends BaseEntity {

    protected AdminStatistic() {
        // mongolink
    }

    public AdminStatistic(String month, Api api) {
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

    public void increment(int increment) {
        count += increment;
    }

    private UUID id = UUID.randomUUID();
    private String month;
    private int count;
    private Api api;

}
