package com.steambeat.repositories.mapping;

import com.steambeat.domain.session.Session;
import org.mongolink.domain.mapper.EntityMap;

public class SessionMapping extends EntityMap<Session> {

    public SessionMapping() {
        super(Session.class);
    }

    @Override
    protected void map() {
        id(element().getToken()).natural();
        property(element().getEmail());
        property(element().getExpirationDate());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
