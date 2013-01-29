package com.feelhub.web.resources.admin;

import com.feelhub.domain.admin.Api;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class AdminStatisticsResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("admin/statistics.ftl").with("alchemyStatistics", Repositories.adminStatistics().getAll(Api.Alchemy)).with("bingSearchStatistics", Repositories.adminStatistics().getAll(Api.BingSearch));
    }
}
