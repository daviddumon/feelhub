package com.feelhub.repositories.mapping;

import com.feelhub.domain.statistics.Statistics;
import org.mongolink.domain.mapper.AggregateMap;

public class StatisticsMapping extends AggregateMap<Statistics> {

    public StatisticsMapping() {
        super(Statistics.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getDate());
        property(element().getTopicId());
        property(element().getGranularity());
        property(element().getBad());
        property(element().getGood());
        property(element().getNeutral());
    }
}
