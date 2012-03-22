package com.steambeat.test.testFactories;

import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;

public class JudgmentFactoryForTest {

    public Judgment newJudgment() {
        final Subject subject = TestFactories.subjects().newWebPage();
        return new Judgment(subject, Feeling.good);
    }

    public Judgment newBadJudgment() {
        final Subject subject = TestFactories.subjects().newWebPage();
        return new Judgment(subject, Feeling.bad);
    }

    public Judgment newGoodJudgment() {
        final Subject subject = TestFactories.subjects().newWebPage();
        return new Judgment(subject, Feeling.good);
    }
}
