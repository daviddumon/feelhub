package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.topic.usable.real.*;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.*;
import org.restlet.resource.*;

public class ApiTopicsResource extends ServerResource {

    @Inject
    public ApiTopicsResource(final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView getTopics() {
        return ModelAndView.createNew("api/topics.json.ftl", MediaType.APPLICATION_JSON).with("topicDatas", Lists.newArrayList());
    }

    @Post
    public void createTopic(final Form form) {
        try {
            checkCredentials();
            final String description = extractDescription(form);
            final RealTopicType typeReal = extractType(form);
            final RealTopic realTopic = topicService.createTopic(CurrentUser.get().getLanguage(), description, typeReal, CurrentUser.get().getUser());
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + realTopic.getId()));
            setStatus(Status.SUCCESS_CREATED);
        } catch (AuthenticationException e) {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }

    private String extractDescription(final Form form) {
        if (form.getQueryString().contains("description")) {
            return form.getFirstValue("description");
        } else {
            throw new FeelhubApiException();
        }
    }

    private RealTopicType extractType(final Form form) {
        if (form.getQueryString().contains("type")) {
            return RealTopicType.valueOf(form.getFirstValue("type"));
        } else {
            throw new FeelhubApiException();
        }
    }

    private final TopicService topicService;
    private TopicDataFactory topicDataFactory;
}
