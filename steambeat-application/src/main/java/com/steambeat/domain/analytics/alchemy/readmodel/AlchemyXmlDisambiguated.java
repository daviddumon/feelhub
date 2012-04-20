package com.steambeat.domain.analytics.alchemy.readmodel;

import com.google.common.collect.Lists;
import org.simpleframework.xml.*;

import java.util.List;

@Root
public class AlchemyXmlDisambiguated {

    public AlchemyXmlDisambiguated() {
        name = "";
        subTypes = Lists.newArrayList();
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

    @Element(required = false)
    public String name;

    @ElementList(inline = true, required = false)
    public List<AlchemyXmlSubtype> subTypes;

    @Element(required = false)
    public String website;

    @Element(required = false)
    public String geo;

    @Element(required = false)
    public String dbpedia;

    @Element(required = false)
    public String yago;

    @Element(required = false)
    public String opencyc;

    @Element(required = false)
    public String umbel;

    @Element(required = false)
    public String freebase;

    @Element(required = false)
    public String ciaFactbook;

    @Element(required = false)
    public String census;

    @Element(required = false)
    public String geonames;

    @Element(required = false)
    public String musicBrainz;

    @Element(required = false)
    public String crunchbase;

    @Element(required = false)
    public String semanticCrunchbase;
}
