package com.feelhub.application;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.feeling.context.SemanticContext;
import com.feelhub.domain.relation.FeelingRelationBinder;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.*;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import java.util.List;

public class FeelingService {

    @Inject
    public FeelingService(final SessionProvider sessionProvider, final FeelingRelationBinder feelingRelationBinder, final SentimentExtractor sentimentExtractor, final TagService tagService) {
        this.sessionProvider = sessionProvider;
        this.feelingRelationBinder = feelingRelationBinder;
        this.sentimentExtractor = sentimentExtractor;
        this.tagService = tagService;
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void handle(final FeelingRequestEvent feelingRequestEvent) {
        sessionProvider.start();
        final List<Sentiment> sentiments = getSentiments(feelingRequestEvent);
        final Feeling feeling = buildFeeling(feelingRequestEvent, sentiments);
        feelingRelationBinder.bind(feeling);
        Repositories.feelings().add(feeling);
        sessionProvider.stop();
    }

    private List<Sentiment> getSentiments(final FeelingRequestEvent feelingRequestEvent) {
        final List<Sentiment> sentiments = Lists.newArrayList();
        sentiments.addAll(fromText(feelingRequestEvent));
        sentiments.addAll(fromFeelingSentiment(feelingRequestEvent));
        return sentiments;
    }

    private List<Sentiment> fromText(final FeelingRequestEvent feelingRequestEvent) {
        final List<Sentiment> result = Lists.newArrayList();
        final List<SentimentAndText> sentimentAndTexts = sentimentExtractor.extract(feelingRequestEvent.getText(), getSemanticContext(feelingRequestEvent));
        for (final SentimentAndText sentimentAndText : sentimentAndTexts) {
            final Tag tag = tagService.lookUpOrCreate(sentimentAndText.text, feelingRequestEvent.getUserLanguage());
            final Sentiment sentiment = new Sentiment(tag.getTopicId(), sentimentAndText.sentimentValue);
            result.add(sentiment);
        }
        return result;
    }

    private SemanticContext getSemanticContext(final FeelingRequestEvent feelingRequestEvent) {
        final SemanticContext semanticContext = new SemanticContext();
        semanticContext.extractFor(feelingRequestEvent.getKeywordValue(), FeelhubLanguage.fromCode(feelingRequestEvent.getUserLanguage().getCode()));
        return semanticContext;
    }

    private List<Sentiment> fromFeelingSentiment(final FeelingRequestEvent feelingRequestEvent) {
        final List<Sentiment> result = Lists.newArrayList();
        final Tag tag = tagService.lookUpOrCreate(feelingRequestEvent.getKeywordValue(), feelingRequestEvent.getLanguage());
        final Sentiment sentiment = new Sentiment(tag.getTopicId(), feelingRequestEvent.getSentimentValue());
        result.add(sentiment);
        return result;
    }

    private Feeling buildFeeling(final FeelingRequestEvent feelingRequestEvent, final List<Sentiment> sentiments) {
        final Feeling.Builder builder = new Feeling.Builder();
        builder.id(feelingRequestEvent.getFeelingId());
        builder.text(feelingRequestEvent.getText());
        builder.user(feelingRequestEvent.getUserId());
        builder.language(feelingRequestEvent.getUserLanguage().getCode());
        builder.sentiments(sentiments);
        return builder.build();
    }

    private final SessionProvider sessionProvider;
    private final TagService tagService;
    private final FeelingRelationBinder feelingRelationBinder;
    private final SentimentExtractor sentimentExtractor;
}
