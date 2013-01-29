package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.admin.*;

public class FakeAlchemyStatisticsRepository extends FakeRepository<AlchemyStatistic> implements AlchemyStatisticsRepository {

    @Override
    public AlchemyStatistic byMonth(String month) {
        for (AlchemyStatistic stat : getAll()) {
            if (month.equals(stat.getMonth())) {
                return stat;
            }
        }
        return null;
    }
}
