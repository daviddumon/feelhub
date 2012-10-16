package com.steambeat.web.resources.json;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.user.User;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.*;

import java.util.UUID;

public class JsonCreateOpinionResource extends ServerResource {

    @Post
    public JsonRepresentation post(final JsonRepresentation jsonRepresentation) {
        try {
            checkCredentials();
            final JSONObject jsonOpinion = jsonRepresentation.getJsonObject();
            final OpinionRequestEvent event = getEventBuilderFrom(jsonOpinion).build();
            DomainEventBus.INSTANCE.post(event);
            setStatus(Status.SUCCESS_CREATED);
            return new JsonRepresentation(getJsonResponse(event));
        } catch (JSONException e) {
            e.printStackTrace();
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        }
        return new JsonRepresentation(new JSONObject());
    }

    private void checkCredentials() throws AuthenticationException {
        if (!getRequest().getAttributes().containsKey("com.steambeat.authentificated") || getRequest().getAttributes().get("com.steambeat.authentificated").equals(false)) {
            throw new AuthenticationException();
        }
    }

    private OpinionRequestEvent.Builder getEventBuilderFrom(final JSONObject jsonOpinion) throws JSONException, AuthenticationException {
        final OpinionRequestEvent.Builder builder = new OpinionRequestEvent.Builder();
        builder.user(extractUser());
        builder.feeling(extractFeeling(jsonOpinion));
        builder.text(extractText(jsonOpinion));
        builder.keywordValue(extractKeywordValue(jsonOpinion));
        builder.languageCode(extractLanguageCode(jsonOpinion));
        builder.userLanguageCode(extractUserLanguageCode(jsonOpinion));
        builder.opinionId(UUID.randomUUID());
        return builder;
    }

    private String extractText(final JSONObject jsonOpinion) throws JSONException {
        return jsonOpinion.get("text").toString();
    }

    private Feeling extractFeeling(final JSONObject jsonOpinion) throws JSONException {
        return Feeling.valueOf(jsonOpinion.get("feeling").toString());
    }

    private String extractKeywordValue(final JSONObject jsonOpinion) throws JSONException {
        return jsonOpinion.get("keywordValue").toString();
    }

    private String extractLanguageCode(final JSONObject jsonOpinion) throws JSONException {
        return jsonOpinion.get("languageCode").toString();
    }

    private String extractUserLanguageCode(final JSONObject jsonOpinion) throws JSONException {
        return jsonOpinion.get("userLanguageCode").toString();
    }

    private User extractUser() throws AuthenticationException {
        if (getRequest().getAttributes().containsKey("com.steambeat.user")) {
            return (User) getRequest().getAttributes().get("com.steambeat.user");
        } else {
            throw new AuthenticationException();

        }
    }

    private JSONObject getJsonResponse(final OpinionRequestEvent event) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("referenceId", event.getOpinionId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
