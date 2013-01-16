package com.feelhub.domain.feeling.analyze;

import com.feelhub.application.TopicService;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.TopicIdentifier;
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

    public List<Sentiment> analyze(final String text, final UUID topicId, final UUID userId) {
        final List<Sentiment> sentiments = Lists.newArrayList();
        //todo : semanticContext
        final Map<String, String> parseResults = textParser.parse(text, new ArrayList<String>());
        final Iterator<Map.Entry<String, String>> iterator = parseResults.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, String> tokenAndSentiment = iterator.next();
            if (tokenAndSentiment.getKey().isEmpty()) {
                final Sentiment sentiment = new Sentiment(topicId, getSentimentValue(tokenAndSentiment.getValue()));
                sentiments.add(sentiment);
            } else if (TopicIdentifier.isHttpTopic(tokenAndSentiment.getKey())) {
                //todo get topic if it exists
                //todo si uri exception alors on crée un RealTopic avec l'url
                final HttpTopic httpTopic = topicService.createHttpTopic(tokenAndSentiment.getKey(), userId);
                final Sentiment sentiment = new Sentiment(httpTopic.getId(), getSentimentValue(tokenAndSentiment.getValue()));
                sentiments.add(sentiment);
            } else {
                //todo si dans semanticcontext on met le topicId
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
