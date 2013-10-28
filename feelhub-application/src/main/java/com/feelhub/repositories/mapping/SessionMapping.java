package com.feelhub.repositories.mapping;

import com.feelhub.domain.session.Session;
import org.mongolink.domain.mapper.AggregateMap;

public class SessionMapping extends AggregateMap<Session> {

    public SessionMapping() {
        super(Session.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getToken()).natural();
        property().onProperty(element().getUserId());
        property().onProperty(element().getExpirationDate());
        property().onProperty(element().getCreationDate());
        property().onProperty(element().getLastModificationDate());
    }
}
