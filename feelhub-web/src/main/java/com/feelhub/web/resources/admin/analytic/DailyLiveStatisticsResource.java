package com.feelhub.web.resources.admin.analytic;

import com.feelhub.analytic.live.LiveDailyStatistics;
import com.feelhub.analytic.user.NewUserDailyBehavior;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import org.jongo.Jongo;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import javax.inject.Inject;

public class DailyLiveStatisticsResource extends ServerResource {

    @Inject
    public DailyLiveStatisticsResource(Jongo jongo) {
        this.jongo = jongo;
    }

    @Get
    public ModelAndView represent() {
        Iterable<LiveDailyStatistics> statistics = jongo.getCollection("dailylivestatistics").find().sort("{date:1}").as(LiveDailyStatistics.class);
        Iterable<NewUserDailyBehavior> dailybehavior = jongo.getCollection("newuserdailybehavior").find().sort("{_id:1}").as(NewUserDailyBehavior.class);
        return ModelAndView.createNew("admin/analytic/live.ftl").with("datas", Lists.newArrayList(statistics)).with("dailybehaviors", Lists.newArrayList(dailybehavior));
    }

    private Jongo jongo;
}
