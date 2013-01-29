package com.feelhub.domain.admin;

import com.feelhub.domain.Repository;

import java.util.List;

public interface AdminStatisticsRepository extends Repository<AdminStatistic> {

    AdminStatistic byMonthAndApi(String month, Api api);

    List<AdminStatistic> getAll(Api api);
}
