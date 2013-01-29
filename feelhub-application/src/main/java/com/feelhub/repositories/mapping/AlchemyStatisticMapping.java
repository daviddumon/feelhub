package com.feelhub.repositories.mapping;

import com.feelhub.domain.admin.AlchemyStatistic;
import org.mongolink.domain.mapper.AggregateMap;

public class AlchemyStatisticMapping extends AggregateMap<AlchemyStatistic> {

    public AlchemyStatisticMapping() {
        super(AlchemyStatistic.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getCreationDate());
        property(element().getLastModificationDate());
        property(element().getMonth());
        property(element().getCount());
    }
}
