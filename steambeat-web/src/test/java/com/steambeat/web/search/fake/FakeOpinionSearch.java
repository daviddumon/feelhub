package com.steambeat.web.search.fake;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
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
    public OpinionSearch withTopic(final Reference reference) {
        opinions = Lists.newArrayList(Iterables.filter(opinions, new Predicate<Opinion>() {

            @Override
            public boolean apply(final Opinion opinion) {
                for (final Judgment judgment : opinion.getJudgments()) {
                    if (judgment.getReferenceId().equals(reference.getId())) {
                        return true;
                    }
                }
                return false;
            }
        }));
        return this;
    }

    private List<Opinion> opinions = Repositories.opinions().getAll();
}
