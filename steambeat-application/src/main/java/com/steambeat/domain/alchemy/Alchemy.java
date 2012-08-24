package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.steambeat.domain.BaseEntity;
import org.joda.time.DateTime;

import java.util.*;

public class Alchemy extends BaseEntity {

    //mongolink constructor do not delete
    public Alchemy() {
    }

    public Alchemy(final UUID referenceId) {
        this.referenceId = referenceId;
        this.id = UUID.randomUUID();
        this.creationDate = new DateTime();
        this.lastModificationDate = this.creationDate;
    }

    @Override
    public Object getId() {
        return id;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final UUID referenceId) {
        this.referenceId = referenceId;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
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

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(final DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(final DateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    private UUID id;
    private UUID referenceId;
    private String type = "";
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
    private DateTime creationDate;
    private DateTime lastModificationDate;
}
