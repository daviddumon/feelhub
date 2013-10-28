package com.feelhub.repositories.mapping;

import com.feelhub.domain.user.User;
import org.mongolink.domain.mapper.AggregateMap;

public class UserMapping extends AggregateMap<User> {

    public UserMapping() {
        super(User.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onProperty(element().getEmail());
        property().onProperty(element().getPassword());
        property().onProperty(element().getFullname());
        property().onProperty(element().getLanguageCode());
        property().onProperty(element().getActive());
        property().onProperty(element().getWelcomePanelShow());
        property().onProperty(element().getBookmarkletShow());
        property().onProperty(element().getCreationDate());
        property().onProperty(element().getLastModificationDate());
        collection().onProperty(element().getSocialAuths());
    }
}
