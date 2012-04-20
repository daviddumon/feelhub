package com.steambeat.domain.analytics.alchemy.readmodel;

import org.simpleframework.xml.*;

@Root
public class AlchemyXmlEntity {

    public AlchemyXmlEntity() {
        type = "";
        relevance = 0.0;
        count = 0;
        text = "";
        language = "";
        disambiguated = new AlchemyXmlDisambiguated();
    }

    @Element
    public String type;

    @Element(required = false)
    public double relevance;

    @Element(required = false)
    public int count;

    @Element(required = false)
    public String text;

    @Element(required = false)
    public AlchemyXmlDisambiguated disambiguated;

    public String language;
}
