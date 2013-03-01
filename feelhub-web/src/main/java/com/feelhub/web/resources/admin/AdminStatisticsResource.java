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
        final List<AdminStatisticsByApi> statistics = orderedStatistics();
        return ModelAndView.createNew("admin/adminStatistics.ftl").with("statistics", statistics);
    }

    private List<AdminStatisticsByApi> orderedStatistics() {
        final List<AdminStatisticsByApi> result = Lists.newArrayList();
        for (final Api api : Api.values()) {
            final List<AdminStatistic> statistics = Repositories.adminStatistics().getAll(api);
            result.add(new AdminStatisticsByApi(api, desc(statistics)));
        }
        return result;
    }

    private List<AdminStatistic> desc(final List<AdminStatistic> statistics) {
        final Ordering<AdminStatistic> desc = new Ordering<AdminStatistic>() {
            @Override
            public int compare(final AdminStatistic left, final AdminStatistic right) {
                return right.getCreationDate().compareTo(left.getCreationDate());
            }
        };
        return desc.sortedCopy(statistics);
    }
}
