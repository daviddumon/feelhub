package com.feelhub.domain.alchemy;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.common.collect.Lists;

import java.util.List;

public class NamedEntity {

    public String type = "";
    public Double relevance;
    public List<String> subType = Lists.newArrayList();
    public String website = "";
    public String geo = "";
    public List<String> tags = Lists.newArrayList();
    public String dbpedia = "";
    public String yago = "";
    public String opencyc = "";
    public String umbel = "";
    public String freebase = "";
    public String ciaFactbook = "";
    public String census = "";
    public String geonames = "";
    public String musicBrainz = "";
    public String crunchbase = "";
    public String semanticCrunchbase = "";
    public FeelhubLanguage feelhubLanguage;
}
