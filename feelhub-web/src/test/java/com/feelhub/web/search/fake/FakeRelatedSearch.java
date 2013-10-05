package com.feelhub.web.search.fake;

import com.feelhub.domain.related.Related;
import com.feelhub.repositories.*;
import com.feelhub.web.search.RelatedSearch;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;

import java.util.*;

public class FakeRelatedSearch extends RelatedSearch {

    @Inject
    public FakeRelatedSearch(final SessionProvider provider) {
        super(provider);
    }

    @Override
    public List<Related> execute() {
        return relatedList;
    }

    @Override
    public RelatedSearch withSkip(final int skipValue) {
        relatedList = Lists.newArrayList(Iterables.skip(relatedList, skipValue));
        return this;
    }

    @Override
    public RelatedSearch withLimit(final int limitValue) {
        relatedList = Lists.newArrayList(Iterables.limit(relatedList, limitValue));
        return this;
    }

    @Override
    public RelatedSearch withTopicId(final UUID topicId) {
        relatedList = Lists.newArrayList(Iterables.filter(relatedList, new Predicate<Related>() {

            @Override
            public boolean apply(final Related related) {
                if (related.getFromId().equals(topicId)) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    private List<Related> relatedList = Repositories.related().getAll();
}
