package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.topic.*;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.*;
import org.restlet.resource.*;

public class ApiTopicsResource extends ServerResource {

    @Inject
    public ApiTopicsResource(final TopicService topicService) {
        this.topicService = topicService;
    }

    @Post
    public void createTopic(final Form form) {
        try {
            checkCredentials();
            final String description = extractDescription(form);
            final TopicType type = extractType(form);
            final Topic topic = topicService.createTopic(CurrentUser.get().getLanguage(), description, type, CurrentUser.get().getUser());
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + topic.getId()));
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

    private TopicType extractType(final Form form) {
        if (form.getQueryString().contains("type")) {
            return TopicType.valueOf(form.getFirstValue("type"));
        } else {
            throw new FeelhubApiException();
        }
    }

    private TopicService topicService;
}
