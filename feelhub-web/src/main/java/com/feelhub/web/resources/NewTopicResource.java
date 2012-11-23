package com.feelhub.web.resources;

import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.resource.*;

public class NewTopicResource extends ServerResource {

    @Inject
    public NewTopicResource(final TopicDataFactory topicDataFactory) {
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView newTopic() {
        final String description = getRequestAttributes().get("description").toString().trim();
        final TopicData topicData = topicDataFactory.getTopicData(description);
        return ModelAndView.createNew("newtopic.ftl").with("topicData", topicData);
    }

    private TopicDataFactory topicDataFactory;
}
