package com.feelhub.domain.admin;

import com.feelhub.domain.Repository;

public interface AlchemyStatisticsRepository extends Repository<AlchemyStatistic> {

    AlchemyStatistic byMonth(String month);
}
