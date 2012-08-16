package com.steambeat.test.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.steambeat.domain.relation.*;
import com.steambeat.domain.reference.Reference;

import javax.annotation.Nullable;

public class FakeRelationRepository extends FakeRepository<Relation> implements RelationRepository {

    @Override
    public Relation lookUp(final Reference from, final Reference to) {
        try {
            return Iterables.find(getAll(), new Predicate<Relation>() {

                @Override
                public boolean apply(@Nullable final Relation input) {
                    if (input.getFromId().equals(from.getId()) && input.getToId().equals(to.getId())) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return null;
        }
    }
}
