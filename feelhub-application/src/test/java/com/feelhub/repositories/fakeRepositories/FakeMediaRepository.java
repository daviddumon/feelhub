package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.media.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;

import java.util.*;

public class FakeMediaRepository extends FakeRepository<Media> implements MediaRepository {

    @Override
    public Media lookUp(final UUID fromId, final UUID toId) {
        try {
            return Iterables.find(getAll(), new Predicate<Media>() {

                @Override
                public boolean apply(final Media input) {
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
    public List<Media> containingTopicId(final UUID topicId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Media>() {

            @Override
            public boolean apply(final Media input) {
                return input.getFromId().equals(topicId) || input.getToId().equals(topicId);
            }
        }));
    }
}
