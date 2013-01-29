package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.admin.AlchemyStatistic;
import com.feelhub.domain.admin.AlchemyStatisticsRepository;
import com.feelhub.domain.alchemy.AlchemyAnalysis;
import com.feelhub.domain.alchemy.AlchemyAnalysisRepository;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;

public class FakeAlchemyStatisticsRepository extends FakeRepository<AlchemyStatistic> implements AlchemyStatisticsRepository {

    @Override
    public AlchemyStatistic byMonth(String month) {
        for(AlchemyStatistic stat : getAll()) {
            if (month.equals(stat.getMonth())) {
                return stat;
            }
        }
        return null;
    }
}
