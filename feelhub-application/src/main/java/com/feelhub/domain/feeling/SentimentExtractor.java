package com.feelhub.domain.feeling;

import com.feelhub.domain.feeling.context.SemanticContext;
import com.feelhub.domain.topic.TopicIdentifier;
import com.google.common.collect.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class SentimentExtractor {

    public List<SentimentAndText> extract(final String text, final SemanticContext semanticContext) {
        final List<SentimentAndText> sentimentAndTexts = Lists.newArrayList();
        final String[] tokens = text.split("\\s");
        for (final String token : tokens) {
            final TreeMap<Integer, SentimentValue> tokenTags = getSemanticTags(token);
            if (hasAny(tokenTags)) {
                extractSentimentAndTextFromSemanticToken(sentimentAndTexts, token, tokenTags);
            } else if (isUri(token)) {
                extractSentimentAndTextFromUri(sentimentAndTexts, token);
            } else if (isInSemanticContext(token, semanticContext)) {
                sentimentAndTexts.add(new SentimentAndText(SentimentValue.none, token));
            }
        }
        return sentimentAndTexts;
    }

    private void extractSentimentAndTextFromSemanticToken(final List<SentimentAndText> sentimentAndTexts, final String token, final TreeMap<Integer, SentimentValue> tokenTags) {
        final SentimentValue tokenSentimentValue = getSentimentValue(tokenTags);
        String cleanedToken = null;
        try {
            cleanedToken = URLDecoder.decode(token.replaceAll(STRING_REPLACE_SEMANTIC, "").toLowerCase(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!isUri(cleanedToken)) {
            cleanedToken = token.replaceAll(STRING_REPLACE_ALL, "").toLowerCase();
            cleanedToken = cleanedToken.replaceAll(STRING_TO_SPACES, " ");
            cleanedToken = removeApostrophe(cleanedToken);
        }
        if (cleanedToken.length() > 2) {
            final SentimentAndText sentimentAndText = new SentimentAndText(tokenSentimentValue, cleanedToken);
            sentimentAndTexts.add(sentimentAndText);
        }
    }

    private void extractSentimentAndTextFromUri(final List<SentimentAndText> sentimentAndTexts, final String token) {
        String cleanedToken;
        try {
            cleanedToken = URLDecoder.decode(token.trim().toLowerCase(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            cleanedToken = "";
            e.printStackTrace();
        }
        if (cleanedToken.length() > 2) {
            final SentimentAndText sentimentAndText = new SentimentAndText(SentimentValue.none, cleanedToken);
            sentimentAndTexts.add(sentimentAndText);
        }
    }

    private boolean isInSemanticContext(final String token, final SemanticContext semanticContext) {
        for (final String value : semanticContext.getValues()) {
            if (token.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    private String removeApostrophe(final String cleanedToken) {
        final int indexOfFirstApostrophe = cleanedToken.indexOf("'");
        if (indexOfFirstApostrophe >= 0) {
            return cleanedToken.substring(indexOfFirstApostrophe + 1, cleanedToken.length());
        }
        return cleanedToken;
    }

    private TreeMap<Integer, SentimentValue> getSemanticTags(final String token) {
        final TreeMap<Integer, SentimentValue> counts = Maps.newTreeMap();
        counts.put(token.lastIndexOf("#"), SentimentValue.none);
        counts.put(token.lastIndexOf("-"), SentimentValue.bad);
        counts.put(token.lastIndexOf("="), SentimentValue.neutral);
        counts.put(token.lastIndexOf("+"), SentimentValue.good);
        return counts;
    }

    private boolean isUri(final String token) {
        return TopicIdentifier.isWebTopic(token);
    }

    private boolean hasAny(final TreeMap<Integer, SentimentValue> map) {
        return map.lastEntry().getKey() >= 0;
    }

    private SentimentValue getSentimentValue(final TreeMap<Integer, SentimentValue> map) {
        return map.lastEntry().getValue();
    }

    private static final String STRING_REPLACE_ALL = "[\\p{Punct}&&[^\\_^\\']]";
    private static final String STRING_REPLACE_SEMANTIC = "[\\+\\-\\=\\#]";
    private static final String STRING_TO_SPACES = "[\\_]";
}
