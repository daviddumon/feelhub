package com.feelhub.repositories.mapping;

import com.feelhub.domain.admin.AdminStatistic;
import org.mongolink.domain.mapper.AggregateMap;

public class AdminStatisticMapping extends AggregateMap<AdminStatistic> {

    public AdminStatisticMapping() {
        super(AdminStatistic.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onProperty(element().getCreationDate());
        property().onProperty(element().getLastModificationDate());
        property().onProperty(element().getMonth());
        property().onProperty(element().getCount());
        property().onProperty(element().getApi());
    }
}
