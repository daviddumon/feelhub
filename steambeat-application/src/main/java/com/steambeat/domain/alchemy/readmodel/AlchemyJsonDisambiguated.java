package com.steambeat.domain.alchemy.readmodel;

import com.google.common.collect.Lists;

import java.util.List;

public class AlchemyJsonDisambiguated {

    public AlchemyJsonDisambiguated() {
        name = "";
        subType = Lists.newArrayList();
        website = "";
        geo = "";
        dbpedia = "";
        yago = "";
        opencyc = "";
        umbel = "";
        freebase = "";
        ciaFactbook = "";
        census = "";
        geonames = "";
        musicBrainz = "";
        crunchbase = "";
        semanticCrunchbase = "";
    }

    public String name;
    public List<String> subType;
    public String website;
    public String geo;
    public String dbpedia;
    public String yago;
    public String opencyc;
    public String umbel;
    public String freebase;
    public String ciaFactbook;
    public String census;
    public String geonames;
    public String musicBrainz;
    public String crunchbase;
    public String semanticCrunchbase;
}
