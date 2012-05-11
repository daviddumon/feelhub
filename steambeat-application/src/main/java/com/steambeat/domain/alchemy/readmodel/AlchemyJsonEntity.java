package com.steambeat.domain.alchemy.readmodel;

public class AlchemyJsonEntity {

    public AlchemyJsonEntity() {
        type = "";
        relevance = 0.0;
        count = 0;
        text = "";
        language = "";
        disambiguated = new AlchemyJsonDisambiguated();
    }

    public String type;
    public double relevance;
    public int count;
    public String text;
    public AlchemyJsonDisambiguated disambiguated;
    public String language;
}
