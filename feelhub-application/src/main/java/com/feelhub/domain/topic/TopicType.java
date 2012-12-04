package com.feelhub.domain.topic;

import com.google.common.collect.Lists;

import java.util.List;

public enum TopicType {
    None(true, false, false),
    World(true, false, false),
    Website(true, false, true),
    Image(true, false, true),
    Video(true, false, true),
    Audio(true, false, true),
    Place(true, false, true),
    Automobile(true, false, true),
    Anniversary(false, true, true),
    Brand(true, false, true),
    City(false, true, true),
    Company(false, false, true),
    Continent(true, true, true),
    Country(true, true, true),
    Drug(true, true, true),
    EntertainmentAward(true, false, true),
    Facility(false, true, true),
    FieldTerminology(false, true, true),
    FinancialMarketIndex(true, false, true),
    GeographicFeature(true, true, true),
    HealthCondition(true, true, true),
    Holiday(true, true, true),
    Movie(false, false, true), //we can have movies with same names!
    MusicGroup(false, false, true), //we can have music bands with same names!
    NaturalDisaster(false, false, true),
    OperatingSystem(true, false, true),
    Organization(false, false, true),
    Other(false, true, true),
    Person(false, false, true),
    PrintMedia(true, false, true),
    RadioProgram(false, false, true),
    RadioStation(true, false, true),
    Region(true, true, true),
    Sport(true, true, true),
    StateOrCounty(true, true, true),
    Technology(false, false, true),
    TelevisionShow(true, false, true),
    TelevisionStation(true, false, true);

    TopicType(final boolean tagUniqueness, final boolean translatable, final boolean usable) {
        this.tagUniqueness = tagUniqueness;
        this.translatable = translatable;
        this.usable = usable;
    }

    public boolean hasTagUniqueness() {
        return tagUniqueness;
    }

    public boolean isTranslatable() {
        return translatable;
    }

    public boolean isUsable() {
        return usable;
    }

    public static List<TopicType> usableValues() {
        List<TopicType> results = Lists.newArrayList();
        for (TopicType topicType : TopicType.values()) {
            if (topicType.isUsable()) {
                results.add(topicType);
            }
        }
        return results;
    }

    private final boolean tagUniqueness;
    private final boolean translatable;
    private final boolean usable;
}
