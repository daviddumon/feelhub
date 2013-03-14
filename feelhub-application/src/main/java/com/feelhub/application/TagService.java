package com.feelhub.application;

import com.feelhub.domain.tag.*;
import com.feelhub.repositories.Repositories;

public class TagService {

    public Tag lookUp(final String value) {
        final Tag tag = Repositories.tags().get(value.toLowerCase());
        if (tag == null) {
            throw new TagNotFoundException();
        }
        return tag;
    }
}