package com.steambeat.domain.opinion;

import com.google.common.collect.Lists;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;

import java.util.List;

public class OpinionTestFactory {

    public Opinion newOpinion() {
        return newOpinionWithText("text");
    }

    public Opinion newOpinionWithText(final String text) {
        return newOpinion(TestFactories.references().newReference(), text);
    }

    public void newOpinions(final int quantity) {
        final Reference reference = TestFactories.references().newReference();
        newOpinions(reference, quantity);
    }

    public List<Opinion> newOpinions(final Reference reference, final int quantity) {
        List<Opinion> result = Lists.newArrayList();
        for (int i = 0; i < quantity; i++) {
            final Opinion opinion = newOpinion(reference, "i" + i);
            result.add(opinion);
        }
        return result;
    }

    public Opinion newOpinion(final Reference reference, final String text) {
        final User activeUser = TestFactories.users().createFakeActiveUser(text + "userforopinion@mail.com");
        final Opinion opinion = new Opinion(text, activeUser.getId());
        opinion.addJudgment(reference, Feeling.bad);
        Repositories.opinions().add(opinion);
        return opinion;
    }

    public Opinion newOpinion(final String text, final Judgment judgment) {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforopinion@mail.com");
        final Opinion opinion = new Opinion(text, activeUser.getId());
        opinion.addJudgment(judgment);
        Repositories.opinions().add(opinion);
        return opinion;
    }

    public Opinion newOpinionWithoutJudgments() {
        final User activeUser = TestFactories.users().createFakeActiveUser("userforopinion@mail.com");
        final Opinion opinion = new Opinion("opinion without judgement", activeUser.getId());
        Repositories.opinions().add(opinion);
        return opinion;
    }
}
