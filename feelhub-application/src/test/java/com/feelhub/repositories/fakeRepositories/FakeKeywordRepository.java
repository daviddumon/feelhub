package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.keyword.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.internal.Nullable;

import java.util.*;

public class FakeKeywordRepository extends FakeRepository<Keyword> implements KeywordRepository {

    @Override
    public Keyword forValueAndLanguage(final String value, final FeelhubLanguage feelhubLanguage) {
        try {
            return Iterables.find(getAll(), new Predicate<Keyword>() {

                @Override
                public boolean apply(@Nullable final Keyword input) {
                    if (input.getValue().equals(value) && input.getLanguage().equals(feelhubLanguage)) {
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
    public List<Keyword> forReferenceId(final UUID referenceId) {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Keyword>() {

            @Override
            public boolean apply(final Keyword input) {
                return input.getReferenceId().equals(referenceId);
            }
        }));
    }

}
