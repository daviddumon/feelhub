package com.feelhub.web.resources.admin.analytic;

import com.feelhub.analytic.live.LiveDailyStatistics;
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
        Iterable<LiveDailyStatistics> statistics = jongo.getCollection("dailylivestatistics").find().sort("{date:1}").as(LiveDailyStatistics.class);
        return ModelAndView.createNew("admin/analytic/daily.ftl").with("datas", Lists.newArrayList(statistics));
    }

    private Jongo jongo;
}
