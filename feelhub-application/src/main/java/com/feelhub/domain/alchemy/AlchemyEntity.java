package com.feelhub.domain.alchemy;

import com.feelhub.domain.BaseEntity;
import com.feelhub.domain.topic.TopicType;
import com.google.common.collect.Lists;

import java.util.*;

public class AlchemyEntity extends BaseEntity {

    //mongolink constructor do not delete
    public AlchemyEntity() {
    }

    public AlchemyEntity(final UUID topicId) {
        this.topicId = topicId;
        this.id = UUID.randomUUID();
    }

    @Override
    public Object getId() {
        return id;
    }

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(final UUID topicId) {
        this.topicId = topicId;
    }

    public TopicType getType() {
        return type;
    }

    public void setType(final TopicType type) {
        this.type = type;
    }

    public List<String> getSubtype() {
        return subtype;
    }

    public void setSubtype(final List<String> subtype) {
        this.subtype = subtype;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(final String website) {
        this.website = website;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(final String geo) {
        this.geo = geo;
    }

    public String getDbpedia() {
        return dbpedia;
    }

    public void setDbpedia(final String dbpedia) {
        this.dbpedia = dbpedia;
    }

    public String getYago() {
        return yago;
    }

    public void setYago(final String yago) {
        this.yago = yago;
    }

    public String getOpencyc() {
        return opencyc;
    }

    public void setOpencyc(final String opencyc) {
        this.opencyc = opencyc;
    }

    public String getUmbel() {
        return umbel;
    }

    public void setUmbel(final String umbel) {
        this.umbel = umbel;
    }

    public String getFreebase() {
        return freebase;
    }

    public void setFreebase(final String freebase) {
        this.freebase = freebase;
    }

    public String getCiafactbook() {
        return ciafactbook;
    }

    public void setCiafactbook(final String ciafactbook) {
        this.ciafactbook = ciafactbook;
    }

    public String getCensus() {
        return census;
    }

    public void setCensus(final String census) {
        this.census = census;
    }

    public String getGeonames() {
        return geonames;
    }

    public void setGeonames(final String geonames) {
        this.geonames = geonames;
    }

    public String getMusicbrainz() {
        return musicbrainz;
    }

    public void setMusicbrainz(final String musicbrainz) {
        this.musicbrainz = musicbrainz;
    }

    public String getCrunchbase() {
        return crunchbase;
    }

    public void setCrunchbase(final String crunchbase) {
        this.crunchbase = crunchbase;
    }

    public String getSemanticcrunchbase() {
        return semanticcrunchbase;
    }

    public void setSemanticcrunchbase(final String semanticcrunchbase) {
        this.semanticcrunchbase = semanticcrunchbase;
    }

    public double getRelevance() {
        return relevance;
    }

    public void setRelevance(final double relevance) {
        this.relevance = relevance;
    }

    public void setNewTopicId(final UUID newTopicId) {
        this.topicId = newTopicId;
    }

    private UUID id;
    private UUID topicId;
    private TopicType type;
    private List<String> subtype = Lists.newArrayList();
    private String website = "";
    private String geo = "";
    private String dbpedia = "";
    private String yago = "";
    private String opencyc = "";
    private String umbel = "";
    private String freebase = "";
    private String ciafactbook = "";
    private String census = "";
    private String geonames = "";
    private String musicbrainz = "";
    private String crunchbase = "";
    private String semanticcrunchbase = "";
    private double relevance;
}
