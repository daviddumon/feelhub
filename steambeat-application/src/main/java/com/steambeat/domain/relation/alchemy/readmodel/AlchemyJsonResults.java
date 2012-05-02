package com.steambeat.domain.relation.alchemy.readmodel;

import com.google.common.collect.Lists;

import java.util.List;

public class AlchemyJsonResults {

    public AlchemyJsonResults() {
        status = "";
        usage = "";
        url = "";
        language = "";
        entities = Lists.newArrayList();
    }

    public String status;
    public String usage;
    public String url;
    public String language;
    public List<AlchemyJsonEntity> entities;
}
