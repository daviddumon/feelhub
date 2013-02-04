package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.*;
import org.restlet.resource.*;

import java.util.List;

public class AdminStatisticsResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        List<AdminStatisticsByApi> statistics = orderedStatistics();
        return ModelAndView.createNew("admin/adminStatistics.ftl").with("statistics", statistics);
    }

    private List<AdminStatisticsByApi> orderedStatistics() {
        List<AdminStatisticsByApi> result = Lists.newArrayList();
        for (Api api : Api.values()) {
            List<AdminStatistic> statistics = Repositories.adminStatistics().getAll(api);
            result.add(new AdminStatisticsByApi(api, desc(statistics)));
        }
        return result;
    }

    private List<AdminStatistic> desc(List<AdminStatistic> statistics) {
        Ordering<AdminStatistic> desc = new Ordering<AdminStatistic>() {
            @Override
            public int compare(AdminStatistic left, AdminStatistic right) {
                return right.getCreationDate().compareTo(left.getCreationDate());
            }
        };
        return desc.sortedCopy(statistics);
    }
}
