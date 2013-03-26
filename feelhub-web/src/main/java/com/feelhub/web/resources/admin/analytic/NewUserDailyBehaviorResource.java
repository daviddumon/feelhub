package com.feelhub.web.resources.admin.analytic;

import com.feelhub.analytic.user.NewUserDailyBehavior;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import org.jongo.Jongo;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;

public class NewUserDailyBehaviorResource extends ServerResource {
    @Inject
    public NewUserDailyBehaviorResource(Jongo jongo) {
        this.jongo = jongo;
    }

    @Get
    public ModelAndView represent() {
        Iterable<NewUserDailyBehavior> dailybehavior = jongo.getCollection("newuserdailybehavior").find().sort("{_id:1}").as(NewUserDailyBehavior.class);
        return ModelAndView.createNew("admin/analytic/newuser.ftl").with("dailybehaviors", Lists.newArrayList(dailybehavior));
    }

    private Jongo jongo;
}
