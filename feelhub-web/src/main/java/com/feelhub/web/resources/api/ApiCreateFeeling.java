package com.feelhub.web.resources.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.feelhub.application.command.Command;
import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.feeling.CreateFeelingCommand;
import com.feelhub.application.command.topic.CreateHttpTopicCommand;
import com.feelhub.application.command.topic.CreateRealTopicCommand;
import com.feelhub.domain.feeling.Sentiment;
import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicIdentifier;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.domain.user.User;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.resources.api.readmodel.SentimentMapper;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ApiCreateFeeling {

    @Inject
    public ApiCreateFeeling(final CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    public JsonRepresentation add(final JSONObject jsonObject) throws JSONException, AuthenticationException {
        final CreateFeelingCommand command = createCommandFrom(jsonObject);
        ListenableFuture<UUID> result = commandBus.execute(command);
        return new JsonRepresentation(getJsonResponse(Futures.getUnchecked(result)));
    }

    private CreateFeelingCommand createCommandFrom(final JSONObject jsonObject) throws JSONException, AuthenticationException {
        final CreateFeelingCommand.Builder builder = new CreateFeelingCommand.Builder();
        builder.user(extractUser());
        builder.text(extractText(jsonObject));
        builder.languageCode(extractLanguageCode(jsonObject));
        builder.sentiments(extractSentiments(jsonObject));
        return builder.build();
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
            language = FeelhubLanguage.fromCode(jsonObject.getString("languageCode"));
            return language;
        } catch (JSONException e) {
            throw new FeelhubApiException();
        }
    }

    private List<Sentiment> extractSentiments(final JSONObject jsonObject) {
        final String data;
        try {
            data = jsonObject.getString("topics");
            final ObjectMapper objectMapper = new ObjectMapper();
            final TypeFactory typeFactory = TypeFactory.defaultInstance();
            final List<SentimentMapper> sentimentMappers = objectMapper.readValue(data, typeFactory.constructCollectionType(List.class, SentimentMapper.class));
            return getSentiments(sentimentMappers);
        } catch (Exception e) {
            throw new FeelhubApiException();
        }
    }

    private List<Sentiment> getSentiments(final List<SentimentMapper> sentimentMappers) {
        final ArrayList<Sentiment> sentiments = Lists.newArrayList();
        for (final SentimentMapper sentimentMapper : sentimentMappers) {
            if (sentimentMapper.id.equalsIgnoreCase("new")) {
                if (TopicIdentifier.isHttpTopic(sentimentMapper.name)) {
                    final CreateHttpTopicCommand command = new CreateHttpTopicCommand(sentimentMapper.name, CurrentUser.get().getUser().getId());
                    createTopicAndAddSentiment(sentiments, sentimentMapper, command);
                } else {
                    final CreateRealTopicCommand command = new CreateRealTopicCommand(language, sentimentMapper.name, RealTopicType.Other, CurrentUser.get().getUser().getId());
                    createTopicAndAddSentiment(sentiments, sentimentMapper, command);
                }
            } else {
                final Sentiment sentiment = new Sentiment(UUID.fromString(sentimentMapper.id), SentimentValue.valueOf(sentimentMapper.sentiment));
                sentiments.add(sentiment);
            }
        }
        return sentiments;
    }

    private void createTopicAndAddSentiment(final ArrayList<Sentiment> sentiments, final SentimentMapper sentimentMapper, final Command<UUID> command) {
        final ListenableFuture<UUID> result = commandBus.execute(command);
        final UUID topicId = Futures.getUnchecked(result);
        final Sentiment sentiment = new Sentiment(topicId, SentimentValue.valueOf(sentimentMapper.sentiment));
        sentiments.add(sentiment);
    }

    private JSONObject getJsonResponse(final UUID feelingId) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", feelingId);
        return jsonObject;
    }

    private FeelhubLanguage language;
    private final CommandBus commandBus;
}
