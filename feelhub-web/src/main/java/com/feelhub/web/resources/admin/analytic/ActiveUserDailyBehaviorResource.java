package com.feelhub.web.resources.admin.analytic;

import com.feelhub.analytic.user.ActiveUserDailyBehavior;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import org.jongo.Jongo;
import org.restlet.resource.*;

import javax.inject.Inject;

public class ActiveUserDailyBehaviorResource extends ServerResource {
    @Inject
    public ActiveUserDailyBehaviorResource(final Jongo jongo) {
        this.jongo = jongo;
    }

    @Get
    public ModelAndView represent() {
        final Iterable<ActiveUserDailyBehavior> dailybehavior = jongo.getCollection("activeuserdailybehavior").find().sort("{_id:1}").as(ActiveUserDailyBehavior.class);
        return ModelAndView.createNew("admin/analytic/activeuser.ftl").with("dailybehaviors", Lists.newArrayList(dailybehavior));
    }

    private final Jongo jongo;
}
