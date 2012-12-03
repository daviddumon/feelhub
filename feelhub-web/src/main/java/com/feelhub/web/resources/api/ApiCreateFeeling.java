package com.feelhub.web.resources.api;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.FeelingRequestEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.web.authentification.CurrentUser;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;

import java.util.UUID;

public class ApiCreateFeeling {

    public JsonRepresentation add(final Form form) throws JSONException, AuthenticationException {
        final FeelingRequestEvent event = getEventBuilderFrom(form).build();
        DomainEventBus.INSTANCE.post(event);
        return new JsonRepresentation(getJsonResponse(event));
    }

    private FeelingRequestEvent.Builder getEventBuilderFrom(final Form form) throws JSONException, AuthenticationException {
        final FeelingRequestEvent.Builder builder = new FeelingRequestEvent.Builder();
        builder.feelingId(UUID.randomUUID());
        builder.topicId(extractId(form));
        builder.user(extractUser());
        builder.text(extractText(form));
        builder.languageCode(extractLanguageCode(form));
        return builder;
    }

    private UUID extractId(final Form form) {
        if (form.getQueryString().contains("topicId")) {
            return UUID.fromString(form.getFirstValue("topicId"));
        } else {
            throw new FeelhubApiException();
        }
    }

    private User extractUser() {
        return CurrentUser.get().getUser();
    }

    private String extractText(final Form form) {
        if (form.getQueryString().contains("text")) {
            return form.getFirstValue("text");
        } else {
            throw new FeelhubApiException();
        }
    }

    private FeelhubLanguage extractLanguageCode(final Form form) {
        if (form.getQueryString().contains("language")) {
            return FeelhubLanguage.fromCode(form.getFirstValue("language"));
        } else {
            throw new FeelhubApiException();
        }
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
