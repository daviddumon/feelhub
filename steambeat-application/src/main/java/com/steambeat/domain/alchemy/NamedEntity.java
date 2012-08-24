package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

import java.util.*;

public class NamedEntity {

    public String name = "";
    public String type = "";
    public SteambeatLanguage steambeatLanguage;
    public Double relevance;
    public List<String> subType = Lists.newArrayList();
    public String website = "";
    public String geo = "";
    public UUID conceptId;
    public List<String> keywords = Lists.newArrayList();
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
}
