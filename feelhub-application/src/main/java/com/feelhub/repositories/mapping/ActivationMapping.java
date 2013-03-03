package com.feelhub.repositories.mapping;

import com.feelhub.domain.user.activation.Activation;
import org.mongolink.domain.mapper.AggregateMap;

public class ActivationMapping extends AggregateMap<Activation> {

    public ActivationMapping() {
        super(Activation.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getUserId());
    }
}
