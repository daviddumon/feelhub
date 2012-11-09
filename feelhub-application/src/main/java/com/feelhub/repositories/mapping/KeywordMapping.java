package com.feelhub.repositories.mapping;

import com.feelhub.domain.keyword.Keyword;
import org.mongolink.domain.mapper.EntityMap;

public class KeywordMapping extends EntityMap<Keyword> {

    public KeywordMapping() {
        super(Keyword.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getValue());
        property(element().getLanguageCode());
        property(element().getTopicId());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
        property(element().isTranslationNeeded());
    }
}
