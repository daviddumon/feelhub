package com.feelhub.web.resources.json;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.user.User;
import com.feelhub.web.authentification.CurrentUser;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.*;

import java.util.UUID;

public class JsonCreateFeelingResource extends ServerResource {

    @Post
    public JsonRepresentation add(final JsonRepresentation jsonRepresentation) {
        try {
            checkCredentials();
            final JSONObject jsonFeeling = jsonRepresentation.getJsonObject();
            final FeelingRequestEvent event = getEventBuilderFrom(jsonFeeling).build();
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
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }

    private FeelingRequestEvent.Builder getEventBuilderFrom(final JSONObject jsonFeeling) throws JSONException, AuthenticationException {
        final FeelingRequestEvent.Builder builder = new FeelingRequestEvent.Builder();
        builder.user(extractUser());
        builder.sentimentValue(extractSentimentValue(jsonFeeling));
        builder.text(extractText(jsonFeeling));
        builder.keywordValue(extractKeywordValue(jsonFeeling));
        builder.languageCode(extractLanguageCode(jsonFeeling));
        builder.userLanguageCode(extractUserLanguageCode(jsonFeeling));
        builder.feelingId(UUID.randomUUID());
        return builder;
    }

    private String extractText(final JSONObject jsonFeeling) throws JSONException {
        return jsonFeeling.get("text").toString();
    }

    private SentimentValue extractSentimentValue(final JSONObject jsonFeeling) throws JSONException {
        return SentimentValue.valueOf(jsonFeeling.get("sentimentValue").toString());
    }

    private String extractKeywordValue(final JSONObject jsonFeeling) throws JSONException {
        return jsonFeeling.get("keywordValue").toString();
    }

    private String extractLanguageCode(final JSONObject jsonFeeling) throws JSONException {
        return jsonFeeling.get("languageCode").toString();
    }

    private String extractUserLanguageCode(final JSONObject jsonFeeling) throws JSONException {
        return jsonFeeling.get("userLanguageCode").toString();
    }

    private User extractUser() throws AuthenticationException {
        return CurrentUser.get().getUser();
    }

    private JSONObject getJsonResponse(final FeelingRequestEvent event) {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", event.getFeelingId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
