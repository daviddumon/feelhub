package com.feelhub.repositories.mapping;

import com.feelhub.domain.session.Session;
import org.mongolink.domain.mapper.AggregateMap;

public class SessionMapping extends AggregateMap<Session> {

    public SessionMapping() {
        super(Session.class);
    }

    @Override
    protected void map() {
        id(element().getToken()).natural();
        property(element().getUserId());
        property(element().getExpirationDate());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
