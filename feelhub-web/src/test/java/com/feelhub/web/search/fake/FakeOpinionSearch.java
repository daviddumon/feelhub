package com.feelhub.web.search.fake;

import com.feelhub.domain.opinion.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.*;
import com.feelhub.web.search.OpinionSearch;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;

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
    public OpinionSearch withReference(final Reference reference) {
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

    @Override
    public void reset() {
        opinions = Repositories.opinions().getAll();
    }

    private List<Opinion> opinions = Repositories.opinions().getAll();
}
