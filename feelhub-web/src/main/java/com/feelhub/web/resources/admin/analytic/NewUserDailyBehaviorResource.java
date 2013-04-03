package com.feelhub.web.resources.admin.analytic;

import com.feelhub.analytic.user.NewUserDailyBehavior;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import org.jongo.Jongo;
import org.restlet.resource.*;

import javax.inject.Inject;

public class NewUserDailyBehaviorResource extends ServerResource {
    @Inject
    public NewUserDailyBehaviorResource(final Jongo jongo) {
        this.jongo = jongo;
    }

    @Get
    public ModelAndView represent() {
        final Iterable<NewUserDailyBehavior> dailybehavior = jongo.getCollection("newuserdailybehavior").find().sort("{_id:1}").as(NewUserDailyBehavior.class);
        return ModelAndView.createNew("admin/analytic/newuser.ftl").with("dailybehaviors", Lists.newArrayList(dailybehavior));
    }

    private final Jongo jongo;
}
