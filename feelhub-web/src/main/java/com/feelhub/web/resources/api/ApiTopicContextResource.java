package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.*;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.ContextData;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.MediaType;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicContextResource extends ServerResource {

    @Inject
    public ApiTopicContextResource(final TopicService topicService, final TopicContext topicContext) {
        this.topicService = topicService;
        this.topicContext = topicContext;
    }

    @Get
    public ModelAndView getSemanticContext() {
        final Topic topic = getTopic();
        final Map<Tag,Topic> tagTopicMap = topicContext.extractFor(topic.getId(), CurrentUser.get().getLanguage());
        return ModelAndView.createNew("api/context.json.ftl", MediaType.APPLICATION_JSON).with("contextDatas", getContextData(tagTopicMap));
    }

    private Topic getTopic() {
        try {
            final String topicId = getRequestAttributes().get("topicId").toString().trim();
            return topicService.lookUpCurrent(UUID.fromString(topicId));
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private List<ContextData> getContextData(final Map<Tag, Topic> values) {
        List<ContextData> results = Lists.newArrayList();
        final Iterator<Map.Entry<Tag, Topic>> iterator = values.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<Tag, Topic> entry = iterator.next();
            final Tag tag = entry.getKey();
            final Topic topic = entry.getValue();
            final ContextData contextData = new ContextData();
            contextData.setValue(tag.getId());
            contextData.setId(topic.getId());
            contextData.setThumbnailSmall(topic.getThumbnailSmall());
            results.add(contextData);
        }
        return results;
    }

    private TopicService topicService;
    private TopicContext topicContext;
}
