package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.tag.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;

public class FakeTagRepository extends FakeRepository<Tag> implements TagRepository {

    @Override
    public List<Tag> forTopicId(final UUID topicId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Tag>() {

            @Override
            public boolean apply(final Tag input) {
                final List<UUID> ids = input.getTopicIds();
                for (final UUID id : ids) {
                    if (id.equals(topicId)) {
                        return true;
                    }
                }
                return false;
            }
        }));
    }
}
