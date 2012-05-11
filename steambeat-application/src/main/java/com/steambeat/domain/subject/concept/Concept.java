package com.steambeat.domain.subject.concept;

import com.google.common.collect.Lists;
import com.steambeat.domain.association.uri.Uri;
import com.steambeat.domain.scrapers.Scraper;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.thesaurus.*;

import java.util.*;

public class Concept extends Subject {

    // mongolink constructor : do not delete
    public Concept() {
    }

    public Concept(final UUID id) {
        super(id);
    }

    public Concept(final String text) {
        super(UUID.randomUUID());
        this.shortDescription = text;
        this.description = text;
        this.illustration = "";
    }

    @Override
    public void setScraper(final Scraper scraper) {
        update(scraper);
    }

    @Override
    protected void update(final Scraper scraper) {
    }

    @Override
    public String getUriToken() {
        return "/concepts/";
    }

    public String getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type.getDescription();
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

    private String type;
    private List<String> subTypes = Lists.newArrayList();
    private String website;
    private String geo;
}
