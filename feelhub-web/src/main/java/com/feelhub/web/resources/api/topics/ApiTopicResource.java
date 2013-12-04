package com.feelhub.web.resources.api.topics;

import com.feelhub.application.search.TopicSearch;
import com.feelhub.domain.topic.*;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.UUID;

public class ApiTopicResource extends ServerResource {

    @Inject
    public ApiTopicResource(final TopicSearch topicSearch, final TopicDataFactory topicDataFactory) {
        this.topicSearch = topicSearch;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView getTopic() {
        try {
            final String topicId = getRequestAttributes().get("topicId").toString().trim();
            final Topic topic = topicSearch.lookUpCurrent(UUID.fromString(topicId));
            return ModelAndView.createNew("api/topic.json.ftl", MediaType.APPLICATION_JSON).with("topicData", topicDataFactory.topicData(topic, CurrentUser.get().getLanguage()));
        } catch (TopicNotFound e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        }
    }

    private TopicSearch topicSearch;
    private TopicDataFactory topicDataFactory;
}
