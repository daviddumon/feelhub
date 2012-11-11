package com.feelhub.repositories.mapping;

import com.feelhub.domain.user.Activation;
import org.mongolink.domain.mapper.EntityMap;

public class ActivationMapping extends EntityMap<Activation> {

    public ActivationMapping() {
        super(Activation.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getUserId());
    }
}
