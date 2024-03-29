package com.feelhub.web.search.fake;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.repositories.*;
import com.feelhub.web.search.FeelingSearch;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;

import java.util.*;

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
    public FeelingSearch withSkip(final int skipValue) {
        feelings = Lists.newArrayList(Iterables.skip(feelings, skipValue));
        return this;
    }

    @Override
    public FeelingSearch withLimit(final int limitValue) {
        feelings = Lists.newArrayList(Iterables.limit(feelings, limitValue));
        return this;
    }

    @Override
    public FeelingSearch withTopicId(final UUID topicId) {
        feelings = Lists.newArrayList(Iterables.filter(feelings, new Predicate<Feeling>() {

            @Override
            public boolean apply(final Feeling feeling) {
                if (feeling.getTopicId().equals(topicId)) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    public FeelingSearch withUserId(final UUID userID) {
        feelings = Lists.newArrayList(Iterables.filter(feelings, new Predicate<Feeling>() {

            @Override
            public boolean apply(final Feeling feeling) {
                if (feeling.getUserId().equals(userID)) {
                    return true;
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
