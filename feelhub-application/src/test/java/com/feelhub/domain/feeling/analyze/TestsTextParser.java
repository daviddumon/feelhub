package com.feelhub.domain.feeling.analyze;

import com.feelhub.domain.topic.http.uri.Uri;
import com.google.common.collect.*;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class TestsTextParser {

    @Before
    public void before() {
        expectedResults.clear();
        knownsTokens.clear();
    }

    @Test
    public void textAlwaysReferenceEmptyString() {
        expectedResults.put("", "#");

        test("");
    }

    @Test
    public void canParseUriWithProtocol() {
        final String text = "This remembers me https://www.google.fr";
        expectedResults.put("https://www.google.fr", "");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canHandleCorrectlyUris() {
        final String uri = "hTTps://www.GOogle.fr";
        final String text = "This remembers me " + uri;
        expectedResults.put(new Uri(uri).getValue(), "");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canParseUriWithoutProtocol() {
        final String uri = "www.google.fr";
        final String text = "This remembers me " + uri;
        expectedResults.put(new Uri(uri).getValue(), "");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void stackSameUris() {
        final String uri = "www.google.fr";
        final String text = "This remembers me " + uri + " and at some other times http://www.google.fr";
        expectedResults.put(new Uri(uri).getValue(), "");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canHandleUrisWithPunctuation() {
        final String uri = "www.google.fr";
        final String text = "This remembers me .,:" + uri + "!!? and " + uri + ".";
        expectedResults.put(new Uri(uri).getValue(), "");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canExtractSentimentForUris() {
        final String uri = "www.google.fr";
        final String text = "This remembers me .-" + uri + "!!+=?";
        expectedResults.put(new Uri(uri).getValue(), "-+=");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canExtractSentimentForMultipleSameUris() {
        final String uri = "www.google.fr";
        final String text = "This remembers me .-" + uri + "!!+? and ++" + uri + ".#";
        expectedResults.put(new Uri(uri).getValue(), "++#-+");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canParseRealTopic() {
        final String text = "I like +oranges";
        expectedResults.put("oranges", "+");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canParseMultipleSameTopic() {
        final String text = "I like +oranges but oranges-- and #oranges++";
        expectedResults.put("oranges", "#++--+");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canParseMultipleSameTopicWithDifferentCases() {
        final String text = "I like +Oranges but ORANGES-- and #oranges++";
        expectedResults.put("oranges", "#++--+");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canParseKnownTokens() {
        final String text = "I talk about Nicolas";
        expectedResults.put("nicolas", "#");
        expectedResults.put("", "#");
        knownsTokens.add("nicolas");

        test(text);
    }

    @Test
    public void canHandleLoneSentiments() {
        final String text = "## I - talk about Nicolas ++";
        expectedResults.put("", "++-##");

        test(text);
    }

    @Test
    public void canUseAccent() {
        final String text = "I like éléphants+";
        expectedResults.put("éléphants", "+");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canLinkWithUnderscore() {
        final String text = "I like +The_life";
        expectedResults.put("the life", "+");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void canUseApostrophe() {
        final String text = "I like +l'agilité";
        expectedResults.put("agilité", "+");
        expectedResults.put("", "#");

        test(text);
    }

    @Test
    public void attachNumberToContextTopic() {
        final String text = "I like +1 +10 +10000 but +mongo10";
        expectedResults.put("", "+++");
        expectedResults.put("mongo10", "+");

        test(text);
    }

    public void test(final String text) {
        final TextParser textParser = new TextParser();

        Map<String, String> results = textParser.parse(text, knownsTokens);
        assertThat(results).isEqualTo(expectedResults);
    }

    Map<String, String> expectedResults = Maps.newHashMap();
    List<String> knownsTokens = Lists.newArrayList();
}
