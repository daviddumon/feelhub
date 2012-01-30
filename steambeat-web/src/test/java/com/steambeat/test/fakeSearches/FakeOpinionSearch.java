package com.steambeat.test.fakeSearches;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.*;
import com.steambeat.web.search.OpinionSearch;

import java.util.List;

public class FakeOpinionSearch extends OpinionSearch {

    @Inject
    public FakeOpinionSearch(final SessionProvider provider) {
        super(provider);
    }

    @Override
    public List<Opinion> execute() {
        return opinions;
    }

    @Override
    public OpinionSearch withSkip(final int skip) {
        opinions = Lists.newArrayList(Iterables.skip(opinions, skip));
        return this;
    }

    @Override
    public OpinionSearch withLimit(final int limit) {
        opinions = Lists.newArrayList(Iterables.limit(opinions, limit));
        return this;
    }

    @Override
    public OpinionSearch withSubject(final Subject subject) {
        opinions = Lists.newArrayList(Iterables.filter(opinions, new Predicate<Opinion>() {
            @Override
            public boolean apply(final Opinion opinion) {
                return opinion.getSubjectId().equals(subject.getId());
            }
        }));
        return this;
    }

    private List<Opinion> opinions = Repositories.opinions().getAll();
}
