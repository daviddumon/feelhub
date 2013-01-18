package com.feelhub.domain.feeling.analyze;

import com.feelhub.domain.topic.TopicIdentifier;
import com.feelhub.domain.topic.http.uri.Uri;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.regex.*;

public class TextParser {

    public Map<String, String> parse(final String text, final List<String> knownsTokens) {
        final HashMap<String, String> results = Maps.newHashMap();
        final String[] tokens = text.split("\\s");
        for (final String token : tokens) {
            parseToken(results, token, knownsTokens);
        }
        addEmptyStringIfNotPresent(results);
        return results;
    }

    private void parseToken(final HashMap<String, String> results, final String token, final List<String> knownsTokens) {
        final String leftPunctuation = getLeftSentiment(token);
        final String rightPunctuation = getRightSentiment(token);
        final String cleanedToken = getCleanedToken(token);
        if (TopicIdentifier.isHttpTopic(cleanedToken)) {
            handleHttpToken(results, cleanedToken, leftPunctuation, rightPunctuation);
        } else {
            handleRealToken(results, cleanedToken, leftPunctuation, rightPunctuation, knownsTokens);
        }
    }

    private String getLeftSentiment(final String token) {
        final Matcher matcher = Pattern.compile(PUNCT_BEFORE).matcher(token);
        return matcher.find() ? matcher.group() : "";
    }

    private String getRightSentiment(final String token) {
        final Matcher matcher = Pattern.compile(PUNCT_AFTER).matcher(token);
        return matcher.find() ? matcher.group() : "";
    }

    private String getCleanedToken(final String token) {
        return token.replaceAll(PUNCT_BEFORE, "").replaceAll(PUNCT_AFTER, "");
    }

    private void handleHttpToken(final HashMap<String, String> results, final String token, final String leftPunctuation, final String rightPunctuation) {
        final StringBuilder stringBuilder = new StringBuilder();
        getSentiments(leftPunctuation + rightPunctuation, stringBuilder);
        appendToResults(results, new Uri(token).getValue(), stringBuilder);
    }

    private void handleRealToken(final HashMap<String, String> results, final String cleanedToken, final String leftPunctuation, final String rightPunctuation, final List<String> knownsTokens) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (cleanedToken.isEmpty() || isNumber(cleanedToken)) {
            handleEmptyToken(results, leftPunctuation, stringBuilder);
        } else {
            final String key = cleanRealToken(cleanedToken);
            handleCleanedToken(results, leftPunctuation, rightPunctuation, knownsTokens, key, stringBuilder);
        }
    }

    private boolean isNumber(final String cleanedToken) {
        final Matcher matcher = Pattern.compile(NUMBER).matcher(cleanedToken);
        return matcher.matches();
    }

    private String cleanRealToken(final String cleanedToken) {
        return removeApostrophe(cleanedToken.toLowerCase().replaceAll(UNDERSCORE, " "));
    }

    private String removeApostrophe(final String cleanedToken) {
        final int indexOfFirstApostrophe = cleanedToken.indexOf("'");
        if (indexOfFirstApostrophe >= 0) {
            return cleanedToken.substring(indexOfFirstApostrophe + 1, cleanedToken.length());
        }
        return cleanedToken;
    }

    private void handleEmptyToken(final HashMap<String, String> results, final String leftPunctuation, final StringBuilder stringBuilder) {
        getSentiments(leftPunctuation, stringBuilder);
        if (stringBuilder.length() > 0) {
            appendToResults(results, "", stringBuilder);
        }
    }

    private void handleCleanedToken(final HashMap<String, String> results, final String leftPunctuation, final String rightPunctuation, final List<String> knownsTokens, final String key, final StringBuilder stringBuilder) {
        getSentiments(leftPunctuation + rightPunctuation, stringBuilder);
        if (stringBuilder.length() > 0) {
            appendToResults(results, key, stringBuilder);
        } else if (knownsTokens.contains(key)) {
            stringBuilder.append("#");
            appendToResults(results, key, stringBuilder);
        }
    }

    private void getSentiments(final String punctuation, final StringBuilder stringBuilder) {
        final Matcher matcher = Pattern.compile(SENTIMENTS).matcher(punctuation);
        while (matcher.find()) {
            stringBuilder.append(matcher.group());
        }
    }

    private void appendToResults(final HashMap<String, String> results, final String key, final StringBuilder stringBuilder) {
        if (results.containsKey(key)) {
            stringBuilder.append(results.get(key));
        }
        results.put(key, stringBuilder.toString());
    }

    private void addEmptyStringIfNotPresent(final HashMap<String, String> results) {
        if (!results.containsKey("")) {
            results.put("", "#");
        }
    }

    public static final String SENTIMENTS = "[\\+\\-\\=\\#]";

    private static final String PUNCT_BEFORE = "^(\\p{Punct})+";
    private static final String PUNCT_AFTER = "(\\p{Punct})+$";
    private static final String UNDERSCORE = "[\\_]";
    private static final String NUMBER = "^[\\p{Digit}]+$";
}
