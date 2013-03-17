package com.feelhub.web.resources.admin.analytic;

import com.feelhub.analytic.daily.DailyUserStatistic;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import org.jongo.Jongo;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import javax.inject.Inject;

public class DailyUserStatisticsResource extends ServerResource {

    @Inject
    public DailyUserStatisticsResource(Jongo jongo) {
        this.jongo = jongo;
    }

    @Get
    public ModelAndView represent() {
        Iterable<DailyUserStatistic> statistics = jongo.getCollection("dailyuserstatistic").find().sort("{date:1}").as(DailyUserStatistic.class);
        return ModelAndView.createNew("admin/analytic/daily.ftl").with("datas", Lists.newArrayList(statistics));
    }

    private Jongo jongo;
}
