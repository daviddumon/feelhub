package com.feelhub.web.resources;

import com.feelhub.application.TopicService;
import com.feelhub.domain.tag.TagNotFoundException;
import com.feelhub.domain.topic.TopicNotFound;
import com.feelhub.domain.topic.usable.UsableTopic;
import com.feelhub.domain.topic.usable.real.RealTopicType;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

public class NewTopicResource extends ServerResource {

    @Inject
    public NewTopicResource(final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView newTopic() {
        try {
            final String name = getDecodedName();
            final List<String> types = getUsableTypes(name);
            final TopicData topicData = topicDataFactory.getTopicData(name);
            setStatus(Status.SUCCESS_OK);
            return ModelAndView.createNew("newtopic.ftl").with("topicData", topicData).with("types", types);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("error.ftl");
        }
    }

    private List<String> getUsableTypes(final String name) {
        List<String> types = Lists.newArrayList();
        List<String> forbiddenTypes = Lists.newArrayList();
        try {
            forbiddenTypes = getForbiddenTypes(name);
        } catch (TagNotFoundException e) {
        }
        for (RealTopicType type : RealTopicType.values()) {
            if (!forbiddenTypes.contains(type.toString())) {
                types.add(type.toString());
            }
        }
        return types;
    }

    private List<String> getForbiddenTypes(final String name) {
        final List<UsableTopic> topics = topicService.getTopics(name);
        List<String> forbiddenTypes = Lists.newArrayList();
        for (UsableTopic topic : topics) {
            try {
                if (topic.getType().hasTagUniqueness()) {
                    forbiddenTypes.add(topic.getType().toString());
                }
            } catch (TopicNotFound e) {
            }
        }
        return forbiddenTypes;
    }

    public String getDecodedName() {
        try {
            if (getQuery().getQueryString().contains("q")) {
                return URLDecoder.decode(getQuery().getFirstValue("q").toString().trim(), "UTF-8");
            } else {
                throw new FeelhubApiException();
            }
        } catch (UnsupportedEncodingException e) {
            throw new FeelhubApiException();
        }
    }

    private TopicService topicService;
    private TopicDataFactory topicDataFactory;
}
