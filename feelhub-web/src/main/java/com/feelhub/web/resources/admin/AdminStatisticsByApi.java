package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.AdminStatistic;
import com.feelhub.domain.admin.Api;

import java.util.List;

public class AdminStatisticsByApi {

    public AdminStatisticsByApi(Api api, List<AdminStatistic> statistics) {
        this.api = api;
        this.statistics = statistics;
    }

    public Api getApi() {
        return api;
    }

    public List<AdminStatistic> getStatistics() {
        return statistics;
    }

    private Api api;
    private List<AdminStatistic> statistics;
}
