package com.feelhub.repositories.mapping;

import com.feelhub.domain.user.SocialAuth;
import org.mongolink.domain.mapper.ComponentMap;

public class SocialAuthMapping extends ComponentMap<SocialAuth> {


    public SocialAuthMapping() {
        super(SocialAuth.class);
    }

    @Override
    protected void map() {
        property(element().getToken());
        property(element().getNetwork());
        property(element().getId());
    }
}
