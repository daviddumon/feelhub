package com.steambeat.repositories.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.steambeat.domain.relation.*;

import javax.annotation.Nullable;
import java.util.*;

public class FakeRelationRepository extends FakeRepository<Relation> implements RelationRepository {

    @Override
    public Relation lookUp(final UUID fromId, final UUID toId) {
        try {
            return Iterables.find(getAll(), new Predicate<Relation>() {

                @Override
                public boolean apply(@Nullable final Relation input) {
                    if (input.getFromId().equals(fromId) && input.getToId().equals(toId)) {
                        return true;
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Relation> forReferenceId(final UUID referenceId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Relation>() {

            @Override
            public boolean apply(final Relation input) {
                return input.getFromId().equals(referenceId) || input.getToId().equals(referenceId);
            }
        }));
    }
}
