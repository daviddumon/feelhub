package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.meta.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;

public class FakeIllustrationRepository extends FakeRepository<Illustration> implements IllustrationRepository {

    @Override
    public List<Illustration> forTopicId(final UUID topicId) {
        if (topicId == null) {
            throw new NullPointerException();
        }
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Illustration>() {

            @Override
            public boolean apply(final Illustration input) {
                return input.getTopicId().equals(topicId);
            }
        }));
    }
}
