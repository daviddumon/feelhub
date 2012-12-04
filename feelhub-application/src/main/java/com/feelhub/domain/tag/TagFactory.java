package com.feelhub.domain.tag;

public class TagFactory {

    public Tag createTag(final String value) {
        return new Tag(value);
    }
}
