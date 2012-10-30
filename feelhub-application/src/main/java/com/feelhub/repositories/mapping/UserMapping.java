package com.feelhub.repositories.mapping;

import com.feelhub.domain.user.User;
import org.mongolink.domain.mapper.EntityMap;

public class UserMapping extends EntityMap<User> {

    public UserMapping() {
        super(User.class);
    }

    @Override
    protected void map() {
        id(element().getEmail()).natural();
        property(element().getPassword());
        property(element().getFullname());
        property(element().getLanguageCode());
        property(element().getActive());
        property(element().getSecret());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
