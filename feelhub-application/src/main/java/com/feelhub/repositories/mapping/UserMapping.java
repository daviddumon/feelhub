package com.feelhub.repositories.mapping;

import com.feelhub.domain.user.User;
import org.mongolink.domain.mapper.AggregateMap;

public class UserMapping extends AggregateMap<User> {

    public UserMapping() {
        super(User.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getEmail());
        property(element().getPassword());
        property(element().getFullname());
        property(element().getLanguageCode());
        property(element().getActive());
        property(element().getWelcomePanelShow());
        property(element().getBookmarkletShow());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
        collection(element().getSocialAuths());
    }
}
