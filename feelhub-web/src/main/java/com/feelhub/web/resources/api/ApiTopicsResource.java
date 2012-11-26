package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.topic.TopicType;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.*;

public class ApiTopicsResource extends ServerResource {

    @Inject
    public ApiTopicsResource(final TopicService topicService) {
        this.topicService = topicService;
    }

    @Post
    public void createTopic(final JsonRepresentation jsonRepresentation) {
        try {
            checkCredentials();
            //final JSONObject jsonObject = jsonRepresentation.getJsonObject();
            //final String description = jsonObject.get("description").toString();
            //final String type = jsonObject.get("type").toString();
            //final JSONArray jsonArray = jsonRepresentation.getJsonArray();
            //final JSONObject descriptionJson = jsonArray.getJSONObject(0);
            //final JSONObject typeJson = jsonArray.getJSONObject(1);
            //final String description = descriptionJson.get("description").toString();
            //final String type = typeJson.get("type").toString();
            topicService.createTopic(CurrentUser.get().getLanguage(), "tamere", TopicType.valueOf("City"));
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/"));
            setStatus(Status.SUCCESS_CREATED);
        } catch (AuthenticationException e) {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        }
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }

    private TopicService topicService;
}
