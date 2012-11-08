package com.feelhub.domain.feeling;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSubjectExtractor {

    @Test
    public void canHandleSubjectWithSentimentValueNone() {
        testText("#subject", SentimentValue.none, "subject");
        testText(" #subject", SentimentValue.none, "subject");
        testText("  #subject", SentimentValue.none, "subject");
        testText("#subject ", SentimentValue.none, "subject");
        testText("#subject  ", SentimentValue.none, "subject");
        testText("##subject", SentimentValue.none, "subject");
        testText("#subject#", SentimentValue.none, "subject");
        testText("#Subject", SentimentValue.none, "subject");
        testText("#SUBJECT", SentimentValue.none, "subject");
        testText("avant #subject apres", SentimentValue.none, "subject");
        testText("avant  #subject apres", SentimentValue.none, "subject");
        testText("avant #subject  apres", SentimentValue.none, "subject");
        testText("avant    #subject    apres", SentimentValue.none, "subject");
        testText("avant subject# apres", SentimentValue.none, "subject");
        testText("avant subj#ect apres", SentimentValue.none, "subject");
        testText("avant [subj#ect! apres", SentimentValue.none, "subject");
        testText("avant ###subj#ect! apres", SentimentValue.none, "subject");
        testText("avant #+#subj#ect! apres", SentimentValue.none, "subject");
    }

    @Test
    public void canHandleSubjectWithSentimentValueNeutral() {
        testText("=subject", SentimentValue.neutral, "subject");
        testText(" =subject", SentimentValue.neutral, "subject");
        testText("  =subject", SentimentValue.neutral, "subject");
        testText("=subject ", SentimentValue.neutral, "subject");
        testText("=subject  ", SentimentValue.neutral, "subject");
        testText("==subject", SentimentValue.neutral, "subject");
        testText("=subject=", SentimentValue.neutral, "subject");
        testText("=Subject", SentimentValue.neutral, "subject");
        testText("=SUBJECT", SentimentValue.neutral, "subject");
        testText("avant =subject apres", SentimentValue.neutral, "subject");
        testText("avant  =subject apres", SentimentValue.neutral, "subject");
        testText("avant =subject  apres", SentimentValue.neutral, "subject");
        testText("avant    =subject    apres", SentimentValue.neutral, "subject");
        testText("avant subject= apres", SentimentValue.neutral, "subject");
        testText("avant subj=ect apres", SentimentValue.neutral, "subject");
        testText("avant [subj=ect! apres", SentimentValue.neutral, "subject");
        testText("avant ===subj=ect! apres", SentimentValue.neutral, "subject");
        testText("avant =+=subj=ect! apres", SentimentValue.neutral, "subject");
    }

    @Test
    public void canHandleSubjectWithSentimentValueGood() {
        testText("+subject", SentimentValue.good, "subject");
        testText(" +subject", SentimentValue.good, "subject");
        testText("  +subject", SentimentValue.good, "subject");
        testText("+subject ", SentimentValue.good, "subject");
        testText("+subject  ", SentimentValue.good, "subject");
        testText("++subject", SentimentValue.good, "subject");
        testText("+subject+", SentimentValue.good, "subject");
        testText("+Subject", SentimentValue.good, "subject");
        testText("+SUBJECT", SentimentValue.good, "subject");
        testText("avant +subject apres", SentimentValue.good, "subject");
        testText("avant  +subject apres", SentimentValue.good, "subject");
        testText("avant +subject  apres", SentimentValue.good, "subject");
        testText("avant    +subject    apres", SentimentValue.good, "subject");
        testText("avant subject+ apres", SentimentValue.good, "subject");
        testText("avant subj+ect apres", SentimentValue.good, "subject");
        testText("avant [subj+ect! apres", SentimentValue.good, "subject");
        testText("avant +++subj+ect! apres", SentimentValue.good, "subject");
        testText("avant +-+subj+ect! apres", SentimentValue.good, "subject");
    }

    @Test
    public void canHandleSubjectWithSentimentValueBad() {
        testText("-subject", SentimentValue.bad, "subject");
        testText(" -subject", SentimentValue.bad, "subject");
        testText("  -subject", SentimentValue.bad, "subject");
        testText("-subject ", SentimentValue.bad, "subject");
        testText("-subject  ", SentimentValue.bad, "subject");
        testText("--subject", SentimentValue.bad, "subject");
        testText("-subject-", SentimentValue.bad, "subject");
        testText("-Subject", SentimentValue.bad, "subject");
        testText("-SUBJECT", SentimentValue.bad, "subject");
        testText("avant -subject apres", SentimentValue.bad, "subject");
        testText("avant  -subject apres", SentimentValue.bad, "subject");
        testText("avant -subject  apres", SentimentValue.bad, "subject");
        testText("avant    -subject    apres", SentimentValue.bad, "subject");
        testText("avant subject- apres", SentimentValue.bad, "subject");
        testText("avant subj-ect apres", SentimentValue.bad, "subject");
        testText("avant [subj-ect! apres", SentimentValue.bad, "subject");
        testText("avant ---subj-ect! apres", SentimentValue.bad, "subject");
        testText("avant -=-subj-ect! apres", SentimentValue.bad, "subject");
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
        final SubjectExtractor subjectExtractor = new SubjectExtractor();
        final List<Subject> subjects = subjectExtractor.extract(text);
        assertThat("for '" + text + "'", subjects.size(), is(1));
        assertThat("for '" + text + "'", subjects.get(0).sentimentValue, is(sentimentValue));
        assertThat("for '" + text + "'", subjects.get(0).text, is(expected));
    }
}
