package com.steambeat.test.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Nullable;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

public class FakeKeywordRepository extends FakeRepository<Keyword> implements KeywordRepository {

    @Override
    public Keyword forValueAndLanguage(final String value, final SteambeatLanguage steambeatLanguage) {
        try {
            return Iterables.find(getAll(), new Predicate<Keyword>() {

                @Override
                public boolean apply(@Nullable final Keyword input) {
                    if (input.getValue().equals(value) && input.getLanguage().equals(steambeatLanguage)) {
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
}
