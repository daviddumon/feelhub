package com.feelhub.web.api;

import com.feelhub.application.TopicService;
import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.feeling.CreateFeelingCommand;
import com.feelhub.domain.feeling.FeelingValue;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.user.User;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.restlet.ext.json.JsonRepresentation;

import java.util.UUID;

public class ApiCreateFeeling {

    @Inject
    public ApiCreateFeeling(final CommandBus commandBus, final TopicService topicService) {
        this.commandBus = commandBus;
        this.topicService = topicService;
    }

    public JsonRepresentation add(final JSONObject jsonObject) throws JSONException, AuthenticationException {
        final CreateFeelingCommand command = createCommandFrom(jsonObject);
        final ListenableFuture<UUID> result = commandBus.execute(command);
        return new JsonRepresentation(getJsonResponse(Futures.getUnchecked(result)));
    }

    private CreateFeelingCommand createCommandFrom(final JSONObject jsonObject) throws JSONException, AuthenticationException {
        final CreateFeelingCommand.Builder builder = new CreateFeelingCommand.Builder();
        builder.user(extractUser());
        builder.text(extractText(jsonObject));
        builder.languageCode(extractLanguageCode(jsonObject));
        builder.topic(extractTopic(jsonObject));
        builder.feelingValue(extractFeelingValue(jsonObject));
        return builder.build();
    }

    private FeelingValue extractFeelingValue(final JSONObject jsonObject) {
        try {
            return FeelingValue.valueOf(jsonObject.getString("feelingValue"));
        } catch (JSONException e) {
            throw new FeelhubApiException();
        }
    }

    private User extractUser() {
        return CurrentUser.get().getUser();
    }

    private String extractText(final JSONObject jsonObject) {
        try {
            return jsonObject.getString("text");
        } catch (JSONException e) {
            throw new FeelhubApiException();
        }
    }

    private FeelhubLanguage extractLanguageCode(final JSONObject jsonObject) {
        try {
            return FeelhubLanguage.fromCode(jsonObject.getString("languageCode"));
        } catch (JSONException e) {
            throw new FeelhubApiException();
        }
    }

    private Topic extractTopic(final JSONObject jsonObject) {
        try {
            return topicService.lookUpCurrent(UUID.fromString(jsonObject.getString("topicId")));
        } catch (JSONException e) {
            throw new FeelhubApiException();
        }
    }

    private JSONObject getJsonResponse(final UUID feelingId) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", feelingId);
        return jsonObject;
    }

    private final CommandBus commandBus;
    private final TopicService topicService;
}
