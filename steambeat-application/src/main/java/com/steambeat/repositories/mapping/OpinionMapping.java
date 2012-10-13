package com.steambeat.repositories.mapping;

import com.steambeat.domain.opinion.Opinion;
import org.mongolink.domain.mapper.EntityMap;

public class OpinionMapping extends EntityMap<Opinion> {

    public OpinionMapping() {
        super(Opinion.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getUserId());
        property(element().getText());
        property(element().getLanguageCode());
        collection(element().getJudgments());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
    }
}
