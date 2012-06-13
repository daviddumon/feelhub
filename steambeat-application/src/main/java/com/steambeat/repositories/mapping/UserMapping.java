package com.steambeat.repositories.mapping;

import com.steambeat.domain.user.User;
import org.mongolink.domain.mapper.EntityMap;

public class UserMapping extends EntityMap<User> {

    public UserMapping() {
        super(User.class);
    }

    @Override
    protected void map() {
        id(element().getEmail()).natural();
        property(element().getPassword());
    }
}
