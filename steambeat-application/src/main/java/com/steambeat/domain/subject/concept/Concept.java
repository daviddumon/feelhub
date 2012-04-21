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

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public String getDbpedia() {
        return dbpedia.toString();
    }

    public void setDbpedia(final Uri dbpedia) {
        this.dbpedia = dbpedia.toString();
    }

    public String getYago() {
        return yago.toString();
    }

    public void setYago(final Uri yago) {
        this.yago = yago.toString();
    }

    public String getOpencyc() {
        return opencyc.toString();
    }

    public void setOpencyc(final Uri opencyc) {
        this.opencyc = opencyc.toString();
    }

    public String getUmbel() {
        return umbel.toString();
    }

    public void setUmbel(final Uri umbel) {
        this.umbel = umbel.toString();
    }

    public String getFreebase() {
        return freebase.toString();
    }

    public void setFreebase(final Uri freebase) {
        this.freebase = freebase.toString();
    }

    public String getCiaFactbook() {
        return ciaFactbook.toString();
    }

    public void setCiaFactbook(final Uri ciaFactbook) {
        this.ciaFactbook = ciaFactbook.toString();
    }

    public String getCensus() {
        return census.toString();
    }

    public void setCensus(final Uri census) {
        this.census = census.toString();
    }

    public String getGeonames() {
        return geonames.toString();
    }

    public void setGeonames(final Uri geonames) {
        this.geonames = geonames.toString();
    }

    public String getMusicBrainz() {
        return musicBrainz.toString();
    }

    public void setMusicBrainz(final Uri musicBrainz) {
        this.musicBrainz = musicBrainz.toString();
    }

    public String getCrunchbase() {
        return crunchbase.toString();
    }

    public void setCrunchbase(final Uri crunchbase) {
        this.crunchbase = crunchbase.toString();
    }

    public String getSemanticCrunchbase() {
        return semanticCrunchbase.toString();
    }

    public void setSemanticCrunchbase(final Uri semanticCrunchbase) {
        this.semanticCrunchbase = semanticCrunchbase.toString();
    }

    private String text;
    private String language;
    private String type;
    private double relevance;
    private int count;
    private String name;
    private List<String> subTypes = Lists.newArrayList();
    private String website;
    private String geo;
    private String dbpedia;
    private String yago;
    private String opencyc;
    private String umbel;
    private String freebase;
    private String ciaFactbook;
    private String census;
    private String geonames;
    private String musicBrainz;
    private String crunchbase;
    private String semanticCrunchbase;
}
