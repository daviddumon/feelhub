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

    public Language getLanguage() {
        return language;
    }

    void setLanguage(final Language language) {
        this.language = language;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
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

    public Uri getWebsite() {
        return website;
    }

    public void setWebsite(final Uri website) {
        this.website = website;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(final String geo) {
        this.geo = geo;
    }

    public Uri getDbpedia() {
        return dbpedia;
    }

    public void setDbpedia(final Uri dbpedia) {
        this.dbpedia = dbpedia;
    }

    public Uri getYago() {
        return yago;
    }

    public void setYago(final Uri yago) {
        this.yago = yago;
    }

    public Uri getOpencyc() {
        return opencyc;
    }

    public void setOpencyc(final Uri opencyc) {
        this.opencyc = opencyc;
    }

    public Uri getUmbel() {
        return umbel;
    }

    public void setUmbel(final Uri umbel) {
        this.umbel = umbel;
    }

    public Uri getFreebase() {
        return freebase;
    }

    public void setFreebase(final Uri freebase) {
        this.freebase = freebase;
    }

    public Uri getCiaFactbook() {
        return ciaFactbook;
    }

    public void setCiaFactbook(final Uri ciaFactbook) {
        this.ciaFactbook = ciaFactbook;
    }

    public Uri getCensus() {
        return census;
    }

    public void setCensus(final Uri census) {
        this.census = census;
    }

    public Uri getGeonames() {
        return geonames;
    }

    public void setGeonames(final Uri geonames) {
        this.geonames = geonames;
    }

    public Uri getMusicBrainz() {
        return musicBrainz;
    }

    public void setMusicBrainz(final Uri musicBrainz) {
        this.musicBrainz = musicBrainz;
    }

    public Uri getCrunchbase() {
        return crunchbase;
    }

    public void setCrunchbase(final Uri crunchbase) {
        this.crunchbase = crunchbase;
    }

    public Uri getSemanticCrunchbase() {
        return semanticCrunchbase;
    }

    public void setSemanticCrunchbase(final Uri semanticCrunchbase) {
        this.semanticCrunchbase = semanticCrunchbase;
    }

    private String text;
    private Language language;
    private Type type;
    private double relevance;
    private int count;
    private String name;
    private List<String> subTypes = Lists.newArrayList();
    private Uri website;
    private String geo;
    private Uri dbpedia;
    private Uri yago;
    private Uri opencyc;
    private Uri umbel;
    private Uri freebase;
    private Uri ciaFactbook;
    private Uri census;
    private Uri geonames;
    private Uri musicBrainz;
    private Uri crunchbase;
    private Uri semanticCrunchbase;
}
