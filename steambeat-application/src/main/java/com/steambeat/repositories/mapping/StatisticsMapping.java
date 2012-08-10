package com.steambeat.repositories.mapping;

import com.steambeat.domain.statistics.Statistics;
import org.mongolink.domain.mapper.EntityMap;

public class StatisticsMapping extends EntityMap<Statistics> {

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
