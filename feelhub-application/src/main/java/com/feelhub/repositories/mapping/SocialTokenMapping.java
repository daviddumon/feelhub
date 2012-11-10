package com.feelhub.repositories.mapping;

import com.feelhub.domain.user.SocialToken;
import org.mongolink.domain.mapper.ComponentMap;

public class SocialTokenMapping extends ComponentMap<SocialToken> {


    public SocialTokenMapping() {
        super(SocialToken.class);
    }

    @Override
    protected void map() {
        property(element().getValue());
        property(element().getNetwork());
    }
}
