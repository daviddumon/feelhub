package com.feelhub.domain.feeling;

import com.feelhub.domain.feeling.context.SemanticContext;
import com.feelhub.domain.tag.Tag;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSentimentExtractor {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canHandleSentimentWithSentimentValueNone() {
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
    public void canHandleSentimentWithSentimentValueNeutral() {
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
    public void canHandleSentimentWithSentimentValueGood() {
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
    public void canHandleSentimentWithSentimentValueBad() {
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

    @Test
    public void canHandleEncodedText() {
        testText("J'aime beaucoup http%3A%2F%2Fwww.google.fr ! hehe", SentimentValue.none, "http://www.google.fr");
        testText("J'aime beaucoup +http%3A%2F%2Fwww.google.fr ! hehe", SentimentValue.good, "http://www.google.fr");
    }

    @Test
    public void canExtractSentimentWithSemanticContext() {
        final SemanticContext semanticContext = new SemanticContext();
        final Tag word1 = TestFactories.tags().newWord("word1");
        final Tag word2 = TestFactories.tags().newWord("word2");
        TestFactories.relations().newRelation(word1.getTopicId(), word2.getTopicId());
        semanticContext.extractFor(word1.getValue(), word1.getLanguage());

        testTest("J'aime les word2", SentimentValue.none, "word2", semanticContext);
    }

    private void testTest(final String text, final SentimentValue sentimentValue, final String expected, final SemanticContext semanticContext) {
        final SentimentExtractor sentimentExtractor = new SentimentExtractor();
        final List<SentimentAndText> sentimentAndTexts = sentimentExtractor.extract(text, semanticContext);
        assertThat("for '" + text + "'", sentimentAndTexts.size(), is(1));
        assertThat("for '" + text + "'", sentimentAndTexts.get(0).sentimentValue, is(sentimentValue));
        assertThat("for '" + text + "'", sentimentAndTexts.get(0).text, is(expected));
    }

    private void testText(final String text, final SentimentValue sentimentValue, final String expected) {
        final SentimentExtractor sentimentExtractor = new SentimentExtractor();
        final List<SentimentAndText> sentimentAndTexts = sentimentExtractor.extract(text, new SemanticContext());
        assertThat("for '" + text + "'", sentimentAndTexts.size(), is(1));
        assertThat("for '" + text + "'", sentimentAndTexts.get(0).sentimentValue, is(sentimentValue));
        assertThat("for '" + text + "'", sentimentAndTexts.get(0).text, is(expected));
    }
}
