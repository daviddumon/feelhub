package com.steambeat.web.search.fake;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import com.google.inject.Inject;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.relation.Relation;
import com.steambeat.repositories.*;
import com.steambeat.web.search.RelationSearch;

import java.util.List;

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
    public RelationSearch withFrom(final Reference from) {

        relations = Lists.newArrayList(Iterables.filter(relations, new Predicate<Relation>() {

            @Override
            public boolean apply(final Relation relation) {
                if (relation.getFromId().equals(from.getId())) {
                    return true;
                }
                return false;
            }
        }));
        return this;
    }

    private List<Relation> relations = Repositories.relations().getAll();
}
