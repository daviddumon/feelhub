package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.alchemy.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;

public class FakeAlchemyEntityRepository extends FakeRepository<AlchemyEntity> implements AlchemyEntityRepository {

    @Override
    public List<AlchemyEntity> forTopicId(final UUID topicId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<AlchemyEntity>() {

            @Override
            public boolean apply(final AlchemyEntity input) {
                return input.getTopicId().equals(topicId);
            }
        }));
    }
}
