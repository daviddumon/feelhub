package com.steambeat.web.search;

import com.google.inject.Inject;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.repositories.SessionProvider;
import fr.bodysplash.mongolink.domain.criteria.*;

import java.util.List;

public class AssociationSearch {

    @Inject
    public AssociationSearch(final SessionProvider provider) {
        criteria = provider.get().createCriteria(Association.class);
    }

    @SuppressWarnings("unchecked")
    public List<Association> execute() {
        return criteria.list();
    }

    public AssociationSearch withId(final String id) {
        criteria.add(Restrictions.equals("_id", id));
        return this;
    }

    private Criteria criteria;
}
