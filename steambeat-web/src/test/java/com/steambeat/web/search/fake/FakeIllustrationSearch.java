package com.steambeat.web.search.fake;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;
import com.steambeat.domain.illustration.Illustration;
import com.steambeat.repositories.*;
import com.steambeat.web.search.*;

import java.util.*;

public class FakeIllustrationSearch extends IllustrationSearch {

    @Inject
    public FakeIllustrationSearch(final SessionProvider provider) {
        super(provider);
    }

    @Override
    public List<Illustration> execute() {
        return illustrations;
    }

    @Override
    public Search<Illustration> withSkip(final int skip) {
        illustrations = Lists.newArrayList(Iterables.skip(illustrations, skip));
        return this;
    }

    @Override
    public Search<Illustration> withLimit(final int limit) {
        illustrations = Lists.newArrayList(Iterables.limit(illustrations, limit));
        return this;
    }

    @Override
    public Search<Illustration> withReferences(final List<UUID> references) {
        illustrations = Lists.newArrayList(Iterables.filter(illustrations, new Predicate<Illustration>() {

            @Override
            public boolean apply(final Illustration illustration) {
                for (UUID reference : references) {
                    if (illustration.getReferenceId().equals(reference)) {
                        return true;
                    }
                }
                return false;
            }
        }));
        return this;
    }

    private List<Illustration> illustrations = Repositories.illustrations().getAll();
}
