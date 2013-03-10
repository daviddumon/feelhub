package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.related.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;

public class FakeRelatedRepository extends FakeRepository<Related> implements RelatedRepository {

    @Override
    public Related lookUp(final UUID fromId, final UUID toId) {
        try {
            return Iterables.find(getAll(), new Predicate<Related>() {

                @Override
                public boolean apply(final Related input) {
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
    public List<Related> containingTopicId(final UUID topicId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Related>() {

            @Override
            public boolean apply(final Related input) {
                return input.getFromId().equals(topicId) || input.getToId().equals(topicId);
            }
        }));
    }

    @Override
    public List<Related> forTopicId(final UUID topicId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Related>() {

            @Override
            public boolean apply(final Related input) {
                return input.getFromId().equals(topicId);
            }
        }));
    }
}
