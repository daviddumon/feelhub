package com.feelhub.domain.topic;

public enum TopicType {
    None(true, false),
    World(true, false),
    Website(true, false),
    Image(true, false),
    Video(true, false),
    Audio(true, false),
    Place(true, false),
    Automobile(true, false),
    Anniversary(false, true),
    Brand(true, false),
    City(false, true),
    Company(false, false),
    Continent(true, true),
    Country(true, true),
    Drug(true, true),
    EntertainmentAward(true, false),
    Facility(false, true),
    FieldTerminology(false, true),
    FinancialMarketIndex(true, false),
    GeographicFeature(true, true),
    HealthCondition(true, true),
    Holiday(true, true),
    Movie(false, false), //we can have movies with same names!
    MusicGroup(false, false), //we can have music bands with same names!
    NaturalDisaster(false, false),
    OperatingSystem(true, false),
    Organization(false, false),
    Person(false, false),
    PrintMedia(true, false),
    RadioProgram(false, false),
    RadioStation(true, false),
    Region(true, true),
    Sport(true, true),
    StateOrCounty(true, true),
    Technology(false, false),
    TelevisionShow(true, false),
    TelevisionStation(true, false),
    Unknown(false, true);

    TopicType(final boolean tagUniqueness, final boolean translatable) {
        this.tagUniqueness = tagUniqueness;
        this.translatable = translatable;
    }

    public boolean hasTagUniqueness() {
        return tagUniqueness;
    }

    public boolean isTranslatable() {
        return translatable;
    }

    private final boolean tagUniqueness;
    private final boolean translatable;
}
