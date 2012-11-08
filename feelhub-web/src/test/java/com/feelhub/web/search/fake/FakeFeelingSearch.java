package com.feelhub.web.search.fake;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.repositories.*;
import com.feelhub.web.search.FeelingSearch;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;

import java.util.List;

public class FakeFeelingSearch extends FeelingSearch {

    @Inject
    public FakeFeelingSearch(final SessionProvider provider) {
        super(provider);
    }

    @Override
    public List<Feeling> execute() {
        return feelings;
    }

    @Override
    public FeelingSearch withSkip(final int skip) {
        feelings = Lists.newArrayList(Iterables.skip(feelings, skip));
        return this;
    }

    @Override
    public FeelingSearch withLimit(final int limit) {
        feelings = Lists.newArrayList(Iterables.limit(feelings, limit));
        return this;
    }

    @Override
    public FeelingSearch withReference(final Reference reference) {
        feelings = Lists.newArrayList(Iterables.filter(feelings, new Predicate<Feeling>() {

            @Override
            public boolean apply(final Feeling feeling) {
                for (final Sentiment sentiment : feeling.getSentiments()) {
                    if (sentiment.getReferenceId().equals(reference.getId())) {
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
        feelings = Repositories.feelings().getAll();
    }

    private List<Feeling> feelings = Repositories.feelings().getAll();
}
