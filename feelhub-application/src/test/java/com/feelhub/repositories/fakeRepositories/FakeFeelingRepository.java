package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.feeling.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;

public class FakeFeelingRepository extends FakeRepository<Feeling> implements FeelingRepository {

    @Override
    public List<Feeling> forReferenceId(final UUID referenceId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Feeling>() {

            @Override
            public boolean apply(final Feeling input) {
                final List<Sentiment> sentiments = input.getSentiments();
                for (final Sentiment sentiment : sentiments) {
                    if (sentiment.getReferenceId().equals(referenceId)) {
                        return true;
                    }
                }
                return false;
            }
        }));
    }
}
