package com.feelhub.repositories.mapping;

import com.feelhub.domain.statistics.Statistics;
import org.mongolink.domain.mapper.AggregateMap;

public class StatisticsMapping extends AggregateMap<Statistics> {

    public StatisticsMapping() {
        super(Statistics.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onProperty(element().getDate());
        property().onProperty(element().getTopicId());
        property().onProperty(element().getGranularity());
        property().onProperty(element().getBad());
        property().onProperty(element().getGood());
        property().onProperty(element().getNeutral());
    }
}
