package com.steambeat.repositories.mapping;

import com.steambeat.domain.opinion.Judgment;
import fr.bodysplash.mongolink.domain.mapper.ComponentMap;

public class JudgmentMapping extends ComponentMap<Judgment> {

    public JudgmentMapping() {
        super(Judgment.class);
    }

    @Override
    protected void map() {
        property(element().getFeeling());
        property(element().getSubject());
    }
}
