package com.feelhub.web.resources;

import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.resource.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class NewTopicResource extends ServerResource {

    @Inject
    public NewTopicResource(final TopicDataFactory topicDataFactory) {
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView newTopic() {
        final String description = getDecodedDescription();
        final TopicData topicData = topicDataFactory.getTopicData(description);
        return ModelAndView.createNew("newtopic.ftl").with("topicData", topicData).with("types", Lists.newArrayList());
    }

    public String getDecodedDescription() {
        try {
            return URLDecoder.decode(getRequestAttributes().get("description").toString().trim(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private TopicDataFactory topicDataFactory;
}
