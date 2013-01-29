package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.Api;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import org.restlet.resource.*;

import java.util.List;

public class AdminStatisticsResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("admin/adminStatistics.ftl").with("statistics", statistics());
    }

    private List<AdminStatisticsByApi> statistics() {
        List<AdminStatisticsByApi> result = Lists.newArrayList();
        for(Api api : Api.values()) {
            result.add(new AdminStatisticsByApi(api, Repositories.adminStatistics().getAll(api)));
        }
        return result;
    }
}
