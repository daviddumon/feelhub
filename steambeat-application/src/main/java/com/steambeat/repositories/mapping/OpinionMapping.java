package com.steambeat.repositories.mapping;

import com.steambeat.domain.opinion.Opinion;
import org.mongolink.domain.mapper.EntityMap;

public class OpinionMapping extends EntityMap<Opinion> {

    public OpinionMapping() {
        super(Opinion.class);
    }

    @Override
    protected void map() {
        id(element().getId()).auto();
        property(element().getCreationDate());
        property(element().getText());
        collection(element().getJudgments());
    }
}
