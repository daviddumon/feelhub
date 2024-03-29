package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.admin.*;
import com.google.common.collect.Lists;

import java.util.List;

public class FakeAdminStatisticsRepository extends FakeRepository<AdminStatistic> implements AdminStatisticsRepository {

    @Override
    public AdminStatistic byMonthAndApi(final String month, final Api api) {
        for (final AdminStatistic stat : getAll()) {
            if (stat.getApi() == api && month.equals(stat.getMonth())) {
                return stat;
            }
        }
        return null;
    }

    @Override
    public List<AdminStatistic> getAll(final Api api) {
        final List<AdminStatistic> result = Lists.newArrayList();
        for (final AdminStatistic stat : getAll()) {
            if (stat.getApi() == api) {
                result.add(stat);
            }
        }
        return result;
    }
}
