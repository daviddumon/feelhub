package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.feeling.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;

public class FakeFeelingRepository extends FakeRepository<Feeling> implements FeelingRepository {

    @Override
    public List<Feeling> forTopicId(final UUID topicId) {

        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Feeling>() {

            @Override
            public boolean apply(final Feeling input) {
                if (input.getTopicId().equals(topicId)) {
                    return true;
                }
                return false;
            }
        }));
    }
}
