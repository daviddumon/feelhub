package com.feelhub.web.search.fake;

import com.feelhub.domain.relation.Relation;
import com.feelhub.repositories.*;
import com.feelhub.web.search.RelationSearch;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;

import java.util.*;

public class FakeRelationSearch extends RelationSearch {

    @Inject
    public FakeRelationSearch(final SessionProvider provider) {
        super(provider);
    }

    @Override
    public List<Relation> execute() {
        return relations;
    }

    @Override
    public RelationSearch withSkip(final int skip) {
        relations = Lists.newArrayList(Iterables.skip(relations, skip));
        return this;
    }

    @Override
    public RelationSearch withLimit(final int limit) {
        relations = Lists.newArrayList(Iterables.limit(relations, limit));
        return this;
    }

    @Override
    public RelationSearch withTopicId(final UUID topicId) {
        relations = Lists.newArrayList(Iterables.filter(relations, new Predicate<Relation>() {

            @Override
            public boolean apply(final Relation relation) {
                if (relation.getFromId().equals(topicId)) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    private List<Relation> relations = Repositories.relations().getAll();
}
