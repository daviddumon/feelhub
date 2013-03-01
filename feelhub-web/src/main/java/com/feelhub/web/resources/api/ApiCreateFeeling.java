package com.feelhub.web.resources.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.feelhub.application.TopicService;
import com.feelhub.application.command.*;
import com.feelhub.application.command.topic.*;
import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.TopicIdentifier;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.domain.user.User;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.resources.api.readmodel.SentimentMapper;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.restlet.ext.json.JsonRepresentation;

import java.util.*;

public class ApiCreateFeeling {

    @Inject
    public ApiCreateFeeling(final TopicService topicService, final CommandBus commandBus) {
        this.commandBus = commandBus;
        final TopicService topicService1 = topicService;
    }

    public JsonRepresentation add(final JSONObject jsonObject) throws JSONException, AuthenticationException {
        final FeelingRequestEvent event = getEvent(jsonObject);
        DomainEventBus.INSTANCE.post(event);
        return new JsonRepresentation(getJsonResponse(event));
    }

    private FeelingRequestEvent getEvent(final JSONObject jsonObject) throws JSONException, AuthenticationException {
        final FeelingRequestEvent.Builder builder = new FeelingRequestEvent.Builder();
        builder.feelingId(UUID.randomUUID());
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

    private JSONObject getJsonResponse(final FeelingRequestEvent event) throws JSONException {
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", event.getFeelingId());
        return jsonObject;
    }

    private FeelhubLanguage language;
    private final CommandBus commandBus;
}
