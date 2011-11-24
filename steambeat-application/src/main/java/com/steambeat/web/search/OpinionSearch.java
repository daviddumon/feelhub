package com.steambeat.web.search;

import com.steambeat.domain.opinion.Opinion;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.SessionProvider;
import fr.bodysplash.mongolink.domain.criteria.*;

import javax.inject.Inject;
import java.util.List;

@SuppressWarnings("unchecked")
public class OpinionSearch {

    @Inject
    public OpinionSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Opinion.class);
        criteria.add(Restrictions.notEquals("text", ""));
    }

    public List<Opinion> execute() {
        return criteria.list();
    }

    public OpinionSearch withSkip(final int skip) {
        criteria.skip(skip);
        return this;
    }

    public OpinionSearch withLimit(final int limit) {
        criteria.limit(limit);
        return this;
    }

    public OpinionSearch withSort(final String sortField, final int sortOrder) {
        criteria.sort(sortField, sortOrder);
        return this;
    }

    public OpinionSearch withSubject(final Subject subject) {
        criteria.add(Restrictions.equals("subjectId", subject.getId()));
        return this;
    }

    private Criteria criteria;
}
