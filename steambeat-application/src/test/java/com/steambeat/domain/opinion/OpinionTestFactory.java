package com.steambeat.domain.opinion;

import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;

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

    public void newOpinions(final Reference reference, final int quantity) {
        for (int i = 0; i < quantity; i++) {
            newOpinion(reference, "i" + i);
        }
    }

    private Opinion newOpinion(final Reference reference, final String text) {
        final User activeUser = TestFactories.users().createActiveUser(text + "userforopinion@mail.com");
        final Opinion opinion = new Opinion(text, activeUser);
        opinion.addJudgment(reference, Feeling.bad);
        Repositories.opinions().add(opinion);
        return opinion;
    }

    public Opinion newOpinion(final String text, final Judgment judgment) {
        final User activeUser = TestFactories.users().createActiveUser("userforopinion@mail.com");
        final Opinion opinion = new Opinion(text, activeUser);
        opinion.addJudgment(judgment);
        Repositories.opinions().add(opinion);
        return opinion;
    }

    public Opinion newOpinionWithoutJudgments() {
        final User activeUser = TestFactories.users().createActiveUser("userforopinion@mail.com");
        final Opinion opinion = new Opinion("opinion without judgement", activeUser);
        Repositories.opinions().add(opinion);
        return opinion;
    }
}
