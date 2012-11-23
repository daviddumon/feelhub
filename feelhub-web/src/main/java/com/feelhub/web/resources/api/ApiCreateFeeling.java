package com.feelhub.web.resources.api;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.web.authentification.CurrentUser;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.restlet.ext.json.JsonRepresentation;

import java.util.UUID;

public class ApiCreateFeeling {

    public JsonRepresentation add(final JsonRepresentation jsonRepresentation) throws JSONException, AuthenticationException {
        final JSONObject jsonFeeling = jsonRepresentation.getJsonObject();
        final FeelingRequestEvent event = getEventBuilderFrom(jsonFeeling).build();
        DomainEventBus.INSTANCE.post(event);
        return new JsonRepresentation(getJsonResponse(event));
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

    private FeelhubLanguage extractLanguageCode(final JSONObject jsonFeeling) throws JSONException {
        return FeelhubLanguage.fromCode(jsonFeeling.get("languageCode").toString());
    }

    private FeelhubLanguage extractUserLanguageCode(final JSONObject jsonFeeling) throws JSONException {
        return FeelhubLanguage.fromCode(jsonFeeling.get("userLanguageCode").toString());
    }

    private User extractUser() {
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
