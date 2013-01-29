package com.feelhub.repositories.mapping;

import com.feelhub.domain.admin.AdminStatistic;
import org.mongolink.domain.mapper.AggregateMap;

public class AdminStatisticMapping extends AggregateMap<AdminStatistic> {

    public AdminStatisticMapping() {
        super(AdminStatistic.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getCreationDate());
        property(element().getLastModificationDate());
        property(element().getMonth());
        property(element().getCount());
        property(element().getApi());
    }
}
