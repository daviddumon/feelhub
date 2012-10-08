package com.steambeat.domain.opinion;

import com.steambeat.domain.reference.Reference;
import com.steambeat.test.TestFactories;

public class JudgmentTestFactory {

    public Judgment newJudgment() {
        final Reference reference = TestFactories.references().newReference();
        return new Judgment(reference.getId(), Feeling.good);
    }

    public Judgment newBadJudgment() {
        final Reference reference = TestFactories.references().newReference();
        return new Judgment(reference.getId(), Feeling.bad);
    }

    public Judgment newGoodJudgment() {
        final Reference reference = TestFactories.references().newReference();
        return new Judgment(reference.getId(), Feeling.good);
    }

    public Judgment newJudgment(final Reference reference, final Feeling feeling) {
        return new Judgment(reference.getId(), feeling);
    }
}
