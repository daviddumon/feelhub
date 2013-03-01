package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.*;

import java.util.List;

public class AdminStatisticsByApi {

    public AdminStatisticsByApi(final Api api, final List<AdminStatistic> statistics) {
        this.api = api;
        this.statistics = statistics;
    }

    public Api getApi() {
        return api;
    }

    public List<AdminStatistic> getStatistics() {
        return statistics;
    }

    private final Api api;
    private final List<AdminStatistic> statistics;
}
