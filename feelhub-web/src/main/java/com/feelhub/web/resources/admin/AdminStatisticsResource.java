package com.feelhub.web.resources.admin;

import com.feelhub.repositories.Repositories;
import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class AdminStatisticsResource extends ServerResource {

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("admin/statistics.ftl").with("alchemyStatistics", Repositories.alchemyStatistics().getAll());
    }
}
