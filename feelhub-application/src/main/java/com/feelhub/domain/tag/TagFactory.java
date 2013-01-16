package com.feelhub.domain.tag;

public class TagFactory {

    public Tag createTag(final String tagValue) {
        return new Tag(tagValue);
    }
}
