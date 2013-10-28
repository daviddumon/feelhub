package com.feelhub.repositories.mapping;

import com.feelhub.domain.user.SocialAuth;
import org.mongolink.domain.mapper.ComponentMap;

public class SocialAuthMapping extends ComponentMap<SocialAuth> {


    public SocialAuthMapping() {
        super(SocialAuth.class);
    }

    @Override
    public void map() {
        property().onProperty(element().getToken());
        property().onProperty(element().getNetwork());
        property().onProperty(element().getId());
    }
}
