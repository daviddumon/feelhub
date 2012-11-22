package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.topic.*;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Nullable;

public class FakeTopicRepository extends FakeRepository<Topic> implements TopicRepository {

    @Override
    public Topic world() {
        try {
            return Iterables.find(getAll(), new Predicate<Topic>() {

                @Override
                public boolean apply(@Nullable final Topic input) {
                    if (input.getType().equals(TopicTypes.world)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            return null;
        }
    }
}
