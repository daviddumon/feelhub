package com.feelhub.web.search.fake;

import com.feelhub.domain.illustration.Illustration;
import com.feelhub.repositories.*;
import com.feelhub.web.search.*;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;

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
    public IllustrationSearch withSkip(final int skip) {
        illustrations = Lists.newArrayList(Iterables.skip(illustrations, skip));
        return this;
    }

    @Override
    public IllustrationSearch withLimit(final int limit) {
        illustrations = Lists.newArrayList(Iterables.limit(illustrations, limit));
        return this;
    }

    @Override
    public IllustrationSearch withTopicIds(final List<UUID> topics) {
        illustrations = Lists.newArrayList(Iterables.filter(illustrations, new Predicate<Illustration>() {

            @Override
            public boolean apply(final Illustration illustration) {
                for (final UUID topic : topics) {
                    if (illustration.getTopicId().equals(topic)) {
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
