package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.feeling.analyze.SemanticContext;
import com.feelhub.domain.relation.FeelingRelationBinder;
import com.feelhub.repositories.Repositories;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.List;

public class FeelingService {

    @Inject
    public FeelingService(final FeelingRelationBinder feelingRelationBinder, final TopicService topicService) {
        this.feelingRelationBinder = feelingRelationBinder;
        this.topicService = topicService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final FeelingRequestEvent feelingRequestEvent) {
        final List<Sentiment> sentiments = getSentiments(feelingRequestEvent);
        final Feeling feeling = buildFeeling(feelingRequestEvent, sentiments);
        //feelingRelationBinder.bind(feeling);
        Repositories.feelings().add(feeling);
    }

    private List<Sentiment> getSentiments(final FeelingRequestEvent feelingRequestEvent) {
        final List<Sentiment> sentiments = Lists.newArrayList();
        sentiments.addAll(fromText(feelingRequestEvent));
        return sentiments;
    }

    private List<Sentiment> fromText(final FeelingRequestEvent feelingRequestEvent) {
        final List<Sentiment> result = Lists.newArrayList();
        //final List<SentimentAndText> sentimentAndTexts = sentimentAndTextExtractor.extract(feelingRequestEvent.getText(), getSemanticContext(feelingRequestEvent));
        //final List<SentimentAndText> sentimentAndTexts = Lists.newArrayList();
        //for (final SentimentAndText sentimentAndText : sentimentAndTexts) {
        //    if (sentimentAndText.text.isEmpty()) {
        //        final Sentiment sentiment = new Sentiment(feelingRequestEvent.getTopicId(), sentimentAndText.sentimentValue);
        //        result.add(sentiment);
        //    } else {
                //        final Tag tag = tagService.lookUpOrCreate(sentimentAndText.text, feelingRequestEvent.getUserLanguage());
                //        final Sentiment sentiment = new Sentiment(tag.getTopicId(), sentimentAndText.sentimentValue);
                //        result.add(sentiment);
            //}
        //}
        return result;
    }

    private SemanticContext getSemanticContext(final FeelingRequestEvent feelingRequestEvent) {
        final SemanticContext semanticContext = new SemanticContext();
        //    semanticContext.extractFor(feelingRequestEvent.getKeywordValue(), FeelhubLanguage.fromCode(feelingRequestEvent.getUserLanguage().getCode()));
        return semanticContext;
    }

    private Feeling buildFeeling(final FeelingRequestEvent feelingRequestEvent, final List<Sentiment> sentiments) {
        final Feeling.Builder builder = new Feeling.Builder();
        builder.id(feelingRequestEvent.getFeelingId());
        builder.text(feelingRequestEvent.getText());
        builder.user(feelingRequestEvent.getUserId());
        builder.language(feelingRequestEvent.getLanguage().getCode());
        builder.sentiments(sentiments);
        return builder.build();
    }

    private final FeelingRelationBinder feelingRelationBinder;
    private TopicService topicService;
}
