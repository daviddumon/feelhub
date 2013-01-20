package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.real.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.authentification.CurrentUser;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.UUID;

public class ApiFeelingSentimentResource extends ServerResource {

    @Inject
    public ApiFeelingSentimentResource(final TopicService topicService) {
        this.topicService = topicService;
    }

    @Put
    public void modifySentiment(final Form form) {
        try {
            checkCredentials();
            feelingId = getRequestAttributes().get("feelingId").toString().trim();
            index = getRequestAttributes().get("index").toString().trim();
            type = RealTopicType.valueOf(form.getFirstValue("type"));
            name = form.getFirstValue("name");
            final RealTopic realTopic = topicService.createRealTopic(CurrentUser.get().getLanguage(), name, type, CurrentUser.get().getUser());
            final Feeling feeling = Repositories.feelings().get(UUID.fromString(feelingId));
            feeling.getSentiments().get(Integer.valueOf(index)).setTopicId(realTopic.getId());
            setStatus(Status.SUCCESS_ACCEPTED);
        } catch (AuthenticationException e) {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        } catch (Exception e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }

    private String feelingId;
    private String index;
    private RealTopicType type;
    private TopicService topicService;
    private String name;
}
