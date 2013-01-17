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
        final SemanticContext semanticContext = new SemanticContext(topicId, language);
        final Map<String, String> parseResults = textParser.parse(text, new ArrayList<String>());
        final Iterator<Map.Entry<String, String>> iterator = parseResults.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, String> tokenAndSentiment = iterator.next();
            if (tokenAndSentiment.getKey().isEmpty()) {
                sentiments.add(new Sentiment(topicId, getSentimentValue(tokenAndSentiment.getValue())));
            } else if (semanticContext.getKnownValues().keySet().contains(tokenAndSentiment.getKey())) {
                sentiments.add(new Sentiment(semanticContext.getKnownValues().get(tokenAndSentiment.getKey()), getSentimentValue(tokenAndSentiment.getValue())));
            } else if (TopicIdentifier.isHttpTopic(tokenAndSentiment.getKey())) {
                final List<Topic> topics = topicService.getTopics(tokenAndSentiment.getKey(), language);
                if (topics.size() == 1) {
                    sentiments.add(new Sentiment(topics.get(0).getId(), getSentimentValue(tokenAndSentiment.getValue())));
                } else {
                    try {
                        final HttpTopic httpTopic = topicService.createHttpTopic(tokenAndSentiment.getKey(), userId);
                        sentiments.add(new Sentiment(httpTopic.getId(), getSentimentValue(tokenAndSentiment.getValue())));
                    } catch (Exception e) {
                        sentiments.add(new Sentiment(getSentimentValue(tokenAndSentiment.getValue())));
                    }
                }
            } else {
                sentiments.add(new Sentiment(getSentimentValue(tokenAndSentiment.getValue())));
            }
        }
        return sentiments;
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
