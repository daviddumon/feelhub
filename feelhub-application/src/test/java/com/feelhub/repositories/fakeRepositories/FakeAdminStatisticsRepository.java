package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.admin.AdminStatistic;
import com.feelhub.domain.admin.AdminStatisticsRepository;
import com.feelhub.domain.admin.Api;
import com.google.common.collect.Lists;

import java.util.List;

public class FakeAdminStatisticsRepository extends FakeRepository<AdminStatistic> implements AdminStatisticsRepository {

    @Override
    public AdminStatistic byMonthAndApi(String month, Api api) {
        for(AdminStatistic stat : getAll()) {
            if (stat.getApi() == api && month.equals(stat.getMonth())) {
                return stat;
            }
        }
        return null;
    }

    @Override
    public List<AdminStatistic> getAll(Api api) {
        List<AdminStatistic> result = Lists.newArrayList();
        for(AdminStatistic stat : getAll()) {
            if (stat.getApi() == api) {
                result.add(stat);
            }
        }
        return result;
    }
}
