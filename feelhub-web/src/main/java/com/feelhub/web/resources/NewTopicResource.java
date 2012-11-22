package com.feelhub.web.resources;

import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class NewTopicResource extends ServerResource {

    @Get
    public ModelAndView newTopic() {
        final String topicname = getRequestAttributes().get("topicname").toString().trim();
        return ModelAndView.createNew("newtopic.ftl").with("topicname", topicname);
    }
}
