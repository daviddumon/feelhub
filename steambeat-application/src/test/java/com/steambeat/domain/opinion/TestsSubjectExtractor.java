package com.steambeat.domain.opinion;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSubjectExtractor {

    @Test
    public void canHandleSubjectWithFeelingNone() {
        testText("#subject", Feeling.none, "subject");
        testText(" #subject", Feeling.none, "subject");
        testText("  #subject", Feeling.none, "subject");
        testText("#subject ", Feeling.none, "subject");
        testText("#subject  ", Feeling.none, "subject");
        testText("##subject", Feeling.none, "subject");
        testText("#subject#", Feeling.none, "subject");
        testText("#Subject", Feeling.none, "subject");
        testText("#SUBJECT", Feeling.none, "subject");
        testText("avant #subject apres", Feeling.none, "subject");
        testText("avant  #subject apres", Feeling.none, "subject");
        testText("avant #subject  apres", Feeling.none, "subject");
        testText("avant    #subject    apres", Feeling.none, "subject");
        testText("avant subject# apres", Feeling.none, "subject");
        testText("avant subj#ect apres", Feeling.none, "subject");
        testText("avant [subj#ect! apres", Feeling.none, "subject");
        testText("avant ###subj#ect! apres", Feeling.none, "subject");
        testText("avant #+#subj#ect! apres", Feeling.none, "subject");
    }

    @Test
    public void canHandleSubjectWithFeelingNeutral() {
        testText("=subject", Feeling.neutral, "subject");
        testText(" =subject", Feeling.neutral, "subject");
        testText("  =subject", Feeling.neutral, "subject");
        testText("=subject ", Feeling.neutral, "subject");
        testText("=subject  ", Feeling.neutral, "subject");
        testText("==subject", Feeling.neutral, "subject");
        testText("=subject=", Feeling.neutral, "subject");
        testText("=Subject", Feeling.neutral, "subject");
        testText("=SUBJECT", Feeling.neutral, "subject");
        testText("avant =subject apres", Feeling.neutral, "subject");
        testText("avant  =subject apres", Feeling.neutral, "subject");
        testText("avant =subject  apres", Feeling.neutral, "subject");
        testText("avant    =subject    apres", Feeling.neutral, "subject");
        testText("avant subject= apres", Feeling.neutral, "subject");
        testText("avant subj=ect apres", Feeling.neutral, "subject");
        testText("avant [subj=ect! apres", Feeling.neutral, "subject");
        testText("avant ===subj=ect! apres", Feeling.neutral, "subject");
        testText("avant =+=subj=ect! apres", Feeling.neutral, "subject");
    }

    @Test
    public void canHandleSubjectWithFeelingGood() {
        testText("+subject", Feeling.good, "subject");
        testText(" +subject", Feeling.good, "subject");
        testText("  +subject", Feeling.good, "subject");
        testText("+subject ", Feeling.good, "subject");
        testText("+subject  ", Feeling.good, "subject");
        testText("++subject", Feeling.good, "subject");
        testText("+subject+", Feeling.good, "subject");
        testText("+Subject", Feeling.good, "subject");
        testText("+SUBJECT", Feeling.good, "subject");
        testText("avant +subject apres", Feeling.good, "subject");
        testText("avant  +subject apres", Feeling.good, "subject");
        testText("avant +subject  apres", Feeling.good, "subject");
        testText("avant    +subject    apres", Feeling.good, "subject");
        testText("avant subject+ apres", Feeling.good, "subject");
        testText("avant subj+ect apres", Feeling.good, "subject");
        testText("avant [subj+ect! apres", Feeling.good, "subject");
        testText("avant +++subj+ect! apres", Feeling.good, "subject");
        testText("avant +-+subj+ect! apres", Feeling.good, "subject");
    }

    @Test
    public void canHandleSubjectWithFeelingBad() {
        testText("-subject", Feeling.bad, "subject");
        testText(" -subject", Feeling.bad, "subject");
        testText("  -subject", Feeling.bad, "subject");
        testText("-subject ", Feeling.bad, "subject");
        testText("-subject  ", Feeling.bad, "subject");
        testText("--subject", Feeling.bad, "subject");
        testText("-subject-", Feeling.bad, "subject");
        testText("-Subject", Feeling.bad, "subject");
        testText("-SUBJECT", Feeling.bad, "subject");
        testText("avant -subject apres", Feeling.bad, "subject");
        testText("avant  -subject apres", Feeling.bad, "subject");
        testText("avant -subject  apres", Feeling.bad, "subject");
        testText("avant    -subject    apres", Feeling.bad, "subject");
        testText("avant subject- apres", Feeling.bad, "subject");
        testText("avant subj-ect apres", Feeling.bad, "subject");
        testText("avant [subj-ect! apres", Feeling.bad, "subject");
        testText("avant ---subj-ect! apres", Feeling.bad, "subject");
        testText("avant -=-subj-ect! apres", Feeling.bad, "subject");
    }

    private void testText(final String text, final Feeling feeling, final String expected) {
        final SubjectExtractor subjectExtractor = new SubjectExtractor();
        final List<Subject> subjects = subjectExtractor.extract(text);
        assertThat("for '" + text + "'", subjects.size(), is(1));
        assertThat("for '" + text + "'", subjects.get(0).feeling, is(feeling));
        assertThat("for '" + text + "'", subjects.get(0).text, is(expected));
    }
}
