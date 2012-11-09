package com.feelhub.domain.topic;

import com.feelhub.domain.feeling.SentimentValue;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsTopicExtractor {

    @Test
    public void canHandleTopicWithSentimentValueNone() {
        testText("#topic", SentimentValue.none, "topic");
        testText(" #topic", SentimentValue.none, "topic");
        testText("  #topic", SentimentValue.none, "topic");
        testText("#topic ", SentimentValue.none, "topic");
        testText("#topic  ", SentimentValue.none, "topic");
        testText("##topic", SentimentValue.none, "topic");
        testText("#topic#", SentimentValue.none, "topic");
        testText("#Topic", SentimentValue.none, "topic");
        testText("#TOPIC", SentimentValue.none, "topic");
        testText("avant #topic apres", SentimentValue.none, "topic");
        testText("avant  #topic apres", SentimentValue.none, "topic");
        testText("avant #topic  apres", SentimentValue.none, "topic");
        testText("avant    #topic    apres", SentimentValue.none, "topic");
        testText("avant topic# apres", SentimentValue.none, "topic");
        testText("avant top#ic apres", SentimentValue.none, "topic");
        testText("avant [top#ic! apres", SentimentValue.none, "topic");
        testText("avant ###top#ic! apres", SentimentValue.none, "topic");
        testText("avant #+#top#ic! apres", SentimentValue.none, "topic");
    }

    @Test
    public void canHandleTopicWithSentimentValueNeutral() {
        testText("=topic", SentimentValue.neutral, "topic");
        testText(" =topic", SentimentValue.neutral, "topic");
        testText("  =topic", SentimentValue.neutral, "topic");
        testText("=topic ", SentimentValue.neutral, "topic");
        testText("=topic  ", SentimentValue.neutral, "topic");
        testText("==topic", SentimentValue.neutral, "topic");
        testText("=topic=", SentimentValue.neutral, "topic");
        testText("=Topic", SentimentValue.neutral, "topic");
        testText("=TOPIC", SentimentValue.neutral, "topic");
        testText("avant =topic apres", SentimentValue.neutral, "topic");
        testText("avant  =topic apres", SentimentValue.neutral, "topic");
        testText("avant =topic  apres", SentimentValue.neutral, "topic");
        testText("avant    =topic    apres", SentimentValue.neutral, "topic");
        testText("avant topic= apres", SentimentValue.neutral, "topic");
        testText("avant top=ic apres", SentimentValue.neutral, "topic");
        testText("avant [top=ic! apres", SentimentValue.neutral, "topic");
        testText("avant ===top=ic! apres", SentimentValue.neutral, "topic");
        testText("avant =+=top=ic! apres", SentimentValue.neutral, "topic");
    }

    @Test
    public void canHandleTopicWithSentimentValueGood() {
        testText("+topic", SentimentValue.good, "topic");
        testText(" +topic", SentimentValue.good, "topic");
        testText("  +topic", SentimentValue.good, "topic");
        testText("+topic ", SentimentValue.good, "topic");
        testText("+topic  ", SentimentValue.good, "topic");
        testText("++topic", SentimentValue.good, "topic");
        testText("+topic+", SentimentValue.good, "topic");
        testText("+Topic", SentimentValue.good, "topic");
        testText("+TOPIC", SentimentValue.good, "topic");
        testText("avant +topic apres", SentimentValue.good, "topic");
        testText("avant  +topic apres", SentimentValue.good, "topic");
        testText("avant +topic  apres", SentimentValue.good, "topic");
        testText("avant    +topic    apres", SentimentValue.good, "topic");
        testText("avant topic+ apres", SentimentValue.good, "topic");
        testText("avant top+ic apres", SentimentValue.good, "topic");
        testText("avant [top+ic! apres", SentimentValue.good, "topic");
        testText("avant +++top+ic! apres", SentimentValue.good, "topic");
        testText("avant +-+top+ic! apres", SentimentValue.good, "topic");
    }

    @Test
    public void canHandleTopicWithSentimentValueBad() {
        testText("-topic", SentimentValue.bad, "topic");
        testText(" -topic", SentimentValue.bad, "topic");
        testText("  -topic", SentimentValue.bad, "topic");
        testText("-topic ", SentimentValue.bad, "topic");
        testText("-topic  ", SentimentValue.bad, "topic");
        testText("--topic", SentimentValue.bad, "topic");
        testText("-topic-", SentimentValue.bad, "topic");
        testText("-Topic", SentimentValue.bad, "topic");
        testText("-TOPIC", SentimentValue.bad, "topic");
        testText("avant -topic apres", SentimentValue.bad, "topic");
        testText("avant  -topic apres", SentimentValue.bad, "topic");
        testText("avant -topic  apres", SentimentValue.bad, "topic");
        testText("avant    -topic    apres", SentimentValue.bad, "topic");
        testText("avant topic- apres", SentimentValue.bad, "topic");
        testText("avant top-ic apres", SentimentValue.bad, "topic");
        testText("avant [top-ic! apres", SentimentValue.bad, "topic");
        testText("avant ---top-ic! apres", SentimentValue.bad, "topic");
        testText("avant -+-top-ic! apres", SentimentValue.bad, "topic");
    }

    @Test
    public void canHandlePunctuation() {
        testText(".+alors, ceci", SentimentValue.good, "alors");
    }

    @Test
    public void canHandleUris() {
        testText("J'aime beaucoup http://www.google.fr ! hehe", SentimentValue.none, "http://www.google.fr");
        testText("J'aime beaucoup www.google.fr ! hehe", SentimentValue.none, "www.google.fr");
    }

    @Test
    public void canHandleUrisWithSentimentValue() {
        testText("J'aime beaucoup +http://www.google.fr ! hehe", SentimentValue.good, "http://www.google.fr");
        testText("J'aime beaucoup -http://www.google.fr ! hehe", SentimentValue.bad, "http://www.google.fr");
        testText("J'aime beaucoup =http://www.google.fr ! hehe", SentimentValue.neutral, "http://www.google.fr");
        testText("J'aime beaucoup #http://www.google.fr ! hehe", SentimentValue.none, "http://www.google.fr");
    }

    @Test
    public void canUseAccent() {
        testText("Ceci est un #éléphant!", SentimentValue.none, "éléphant");
    }

    @Test
    public void canLinkWithUnderscore() {
        testText("c'est #la_mort!", SentimentValue.none, "la mort");
    }

    @Test
    public void canUseApostrophe() {
        testText("Vive +l'Agilité !!", SentimentValue.good, "agilité");
    }

    private void testText(final String text, final SentimentValue sentimentValue, final String expected) {
        final TopicExtractor topicExtractor = new TopicExtractor();
        final List<KeywordAndSentiment> keywordAndSentiments = topicExtractor.extract(text);
        assertThat("for '" + text + "'", keywordAndSentiments.size(), is(1));
        assertThat("for '" + text + "'", keywordAndSentiments.get(0).sentimentValue, is(sentimentValue));
        assertThat("for '" + text + "'", keywordAndSentiments.get(0).text, is(expected));
    }
}
