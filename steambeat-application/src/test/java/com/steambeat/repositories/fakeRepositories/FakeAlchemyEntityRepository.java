package com.steambeat.repositories.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.alchemy.*;

import java.util.*;

public class FakeAlchemyEntityRepository extends FakeRepository<AlchemyEntity> implements AlchemyEntityRepository {

    @Override
    public List<AlchemyEntity> forReferenceId(final UUID referenceId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<AlchemyEntity>() {

            @Override
            public boolean apply(final AlchemyEntity input) {
                return input.getReferenceId().equals(referenceId);
            }
        }));
    }
}
