package com.steambeat.domain.subject.concept;

import com.google.common.collect.Lists;
import com.steambeat.domain.analytics.alchemy.thesaurus.*;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.scrapers.Scraper;
import com.steambeat.domain.subject.Subject;

import java.util.*;

public class Concept extends Subject {

    // mongolink constructor : do not delete
    public Concept() {
    }

    public Concept(final String text) {
        super(UUID.randomUUID());
        this.text = text;
    }

    @Override
    public void setScraper(final Scraper scraper) {
        update(scraper);
    }

    @Override
    protected void update(final Scraper scraper) {
    }

    public String getText() {
        return text;
    }

    public String getLanguage() {
        return language;
    }

    void setLanguage(final Language language) {
        this.language = language.getCode();
    }

    public String getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type.getDescription();
    }

    public double getRelevance() {
        return relevance;
    }

    public void setRelevance(final double relevance) {
        this.relevance = relevance;
    }

    public List<String> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(final List<String> subTypes) {
        this.subTypes = subTypes;
    }

    public String getWebsite() {
        return website.toString();
    }

    public void setWebsite(final Uri website) {
        this.website = website.toString();
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(final String geo) {
        this.geo = geo;
    }

    private String text;
    private String language;
    private String type;
    private double relevance;
    private List<String> subTypes = Lists.newArrayList();
    private String website;
    private String geo;
}
