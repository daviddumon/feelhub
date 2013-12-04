package com.feelhub.web.resources.api.topics;

import com.feelhub.application.search.TopicSearch;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.*;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.ContextData;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicContextResource extends ServerResource {

    @Inject
    public ApiTopicContextResource(final TopicSearch topicSearch, final TopicContext topicContext) {
        this.topicSearch = topicSearch;
        this.topicContext = topicContext;
    }

    @Get
    public ModelAndView getSemanticContext() {
        try {
            final Topic topic = getTopic();
            final Map<Tag, Topic> tagTopicMap = topicContext.extractFor(topic.getId(), CurrentUser.get().getLanguage());
            return ModelAndView.createNew("api/context.json.ftl", MediaType.APPLICATION_JSON).with("contextDatas", getContextData(tagTopicMap));
        } catch (TopicNotFound e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        }
    }

    private Topic getTopic() {
        final String topicId = getRequestAttributes().get("topicId").toString().trim();
        return topicSearch.lookUpCurrent(UUID.fromString(topicId));
    }

    private List<ContextData> getContextData(final Map<Tag, Topic> values) {
        final List<ContextData> results = Lists.newArrayList();
        final Iterator<Map.Entry<Tag, Topic>> iterator = values.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<Tag, Topic> entry = iterator.next();
            final Tag tag = entry.getKey();
            final Topic topic = entry.getValue();
            final ContextData contextData = new ContextData();
            contextData.setValue(tag.getId());
            contextData.setId(topic.getId());
            contextData.setThumbnail(topic.getThumbnail());
            results.add(contextData);
        }
        return results;
    }

    private final TopicSearch topicSearch;
    private final TopicContext topicContext;
}
