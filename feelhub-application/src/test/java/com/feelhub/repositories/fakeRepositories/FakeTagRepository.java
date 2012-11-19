package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.tag.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.internal.Nullable;

import java.util.*;

public class FakeTagRepository extends FakeRepository<Tag> implements TagRepository {

    @Override
    public Tag forValueAndLanguage(final String value, final FeelhubLanguage feelhubLanguage) {
        try {
            return Iterables.find(getAll(), new Predicate<Tag>() {

                @Override
                public boolean apply(@Nullable final Tag input) {
                    return input.getValue().equals(value) && input.getLanguage().equals(feelhubLanguage);
                }
            });
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Tag forTopicIdAndLanguage(final UUID topicId, final FeelhubLanguage feelhubLanguage) {
        try {
            return Iterables.find(getAll(), new Predicate<Tag>() {

                @Override
                public boolean apply(@Nullable final Tag input) {
                    if (input.getTopicId().equals(topicId) && input.getLanguage().equals(feelhubLanguage)) {
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

    @Override
    public List<Tag> forTopicId(final UUID topicId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Tag>() {

            @Override
            public boolean apply(final Tag input) {
                return input.getTopicId().equals(topicId);
            }
        }));
    }
}
