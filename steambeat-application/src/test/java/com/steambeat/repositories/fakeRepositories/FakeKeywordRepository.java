package com.steambeat.repositories.fakeRepositories;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.internal.Nullable;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

import java.util.*;

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
