package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.relation.*;
import com.google.common.base.*;
import com.google.common.collect.*;

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
    public List<Relation> containingTopicId(final UUID topicId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Relation>() {

            @Override
            public boolean apply(final Relation input) {
                return input.getFromId().equals(topicId) || input.getToId().equals(topicId);
            }
        }));
    }

    @Override
    public List<Relation> forTopicId(final UUID topicId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Relation>() {

            @Override
            public boolean apply(final Relation input) {
                return input.getFromId().equals(topicId);
            }
        }));
    }
}
