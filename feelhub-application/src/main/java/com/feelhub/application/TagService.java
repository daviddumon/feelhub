package com.feelhub.application;

import com.feelhub.domain.tag.*;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

public class TagService {

    @Inject
    public TagService(final TagFactory tagFactory) {
        this.tagFactory = tagFactory;
    }

    public Tag lookUp(final String value) {
        final Tag tag = Repositories.tags().get(value.toLowerCase());
        if (tag == null) {
            throw new TagNotFoundException();
        }
        return tag;
    }

    public Tag createTag(final String value) {
        final Tag tag = tagFactory.createTag(value.toLowerCase());
        Repositories.tags().add(tag);
        return tag;
    }

    private final TagFactory tagFactory;
}