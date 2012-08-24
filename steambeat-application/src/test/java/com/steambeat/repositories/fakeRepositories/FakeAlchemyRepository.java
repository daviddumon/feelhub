package com.steambeat.repositories.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.alchemy.*;

import java.util.*;

public class FakeAlchemyRepository extends FakeRepository<Alchemy> implements AlchemyRepository {

    @Override
    public List<Alchemy> forReferenceId(final UUID referenceId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Alchemy>() {

            @Override
            public boolean apply(final Alchemy input) {
                return input.getReferenceId().equals(referenceId);
            }
        }));
    }
}
