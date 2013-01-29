package com.feelhub.domain.admin;

import com.feelhub.domain.Repository;
import com.feelhub.domain.alchemy.AlchemyAnalysis;

import java.util.List;
import java.util.UUID;

public interface AlchemyStatisticsRepository extends Repository<AlchemyStatistic> {

    AlchemyStatistic byMonth(String month);
}
