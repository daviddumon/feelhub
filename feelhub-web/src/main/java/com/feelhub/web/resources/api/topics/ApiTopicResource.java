package com.feelhub.web.resources.api.topics;

import com.feelhub.application.TopicService;
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
    public ApiTopicResource(final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView getTopic() {
        try {
            final String topicId = getRequestAttributes().get("topicId").toString().trim();
            final Topic topic = topicService.lookUpCurrent(UUID.fromString(topicId));
            return ModelAndView.createNew("api/topic.json.ftl", MediaType.APPLICATION_JSON).with("topicData", topicDataFactory.topicData(topic, CurrentUser.get().getLanguage()));
        } catch (TopicNotFound e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        }
    }

    private TopicService topicService;
    private TopicDataFactory topicDataFactory;
}
