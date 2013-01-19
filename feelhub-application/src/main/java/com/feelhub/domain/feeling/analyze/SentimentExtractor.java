package com.feelhub.domain.feeling.analyze;

import com.feelhub.application.TopicService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.http.HttpTopic;
import com.google.common.collect.*;
import com.google.inject.Inject;

import java.util.*;
import java.util.regex.*;

public class SentimentExtractor {

    @Inject
    public SentimentExtractor(final TextParser textParser, final TopicService topicService) {
        this.textParser = textParser;
        this.topicService = topicService;
    }

    public List<Sentiment> analyze(final String text, final UUID topicId, final UUID userId, final FeelhubLanguage language) {
        final List<Sentiment> sentiments = Lists.newArrayList();
        final Map<String, String> parseResults = textParser.parse(text, new ArrayList<String>());
        final SemanticContext semanticContext = new SemanticContext(topicId, language);
        for (Map.Entry<String, String> tokenAndSentiment : parseResults.entrySet()) {
            sentiments.add(getSentiment(topicId, userId, language, getSentimentValue(tokenAndSentiment.getValue()), tokenAndSentiment.getKey(), semanticContext));
        }
        return sentiments;
    }

    private Sentiment getSentiment(UUID topicId, UUID userId, FeelhubLanguage language, SentimentValue sentimentValue, String token, SemanticContext semanticContext) {
        if (token.isEmpty()) {
            return new Sentiment(topicId, sentimentValue);
        } else if (semanticContext.getKnownValues().keySet().contains(token)) {
            return new Sentiment(semanticContext.getKnownValues().get(token), sentimentValue);
        } else if (TopicIdentifier.isHttpTopic(token)) {
            final List<Topic> topics = topicService.getTopics(token, language);
            if (topics.size() == 1) {
                return new Sentiment(topics.get(0).getId(), sentimentValue);
            } else {
                try {
                    final HttpTopic httpTopic = topicService.createHttpTopic(token, userId);
                    return new Sentiment(httpTopic.getId(), sentimentValue);
                } catch (Exception e) {
                    return new Sentiment(sentimentValue, token);
                }
            }
        }
        return new Sentiment(sentimentValue, token);
    }

    private SentimentValue getSentimentValue(final String semanticMarkups) {
        final TreeMap<Integer, SentimentValue> values = Maps.newTreeMap();
        values.put(count(semanticMarkups, "\\+"), SentimentValue.good);
        values.put(count(semanticMarkups, "\\-"), SentimentValue.bad);
        values.put(count(semanticMarkups, "\\="), SentimentValue.neutral);
        values.put(count(semanticMarkups, "\\#"), SentimentValue.none);
        return values.lastEntry().getValue();
    }

    private int count(final String semanticMarkups, final String character) {
        int count = 0;
        final Matcher matcher = Pattern.compile(character).matcher(semanticMarkups);
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private TextParser textParser;
    private TopicService topicService;
}