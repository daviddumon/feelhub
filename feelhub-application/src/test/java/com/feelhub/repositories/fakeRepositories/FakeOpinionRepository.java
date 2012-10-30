package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.opinion.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;

public class FakeOpinionRepository extends FakeRepository<Opinion> implements OpinionRepository {

    @Override
    public List<Opinion> forReferenceId(final UUID referenceId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Opinion>() {

            @Override
            public boolean apply(final Opinion input) {
                final List<Judgment> judgments = input.getJudgments();
                for (final Judgment judgment : judgments) {
                    if (judgment.getReferenceId().equals(referenceId)) {
                        return true;
                    }
                }
                return false;
            }
        }));
    }
}
