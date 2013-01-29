package com.feelhub.domain.admin;

import com.feelhub.domain.BaseEntity;

import java.util.UUID;

public class AlchemyStatistic extends BaseEntity {

    protected AlchemyStatistic() {
        // mongolink
    }

    public AlchemyStatistic(String month) {
        this.month = month;
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

    public void increment() {
        count++;
    }
    private UUID id = UUID.randomUUID();
    private String month;
    private int count;

}
