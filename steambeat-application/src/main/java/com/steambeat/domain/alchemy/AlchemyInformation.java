package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.steambeat.domain.thesaurus.Type;
import com.steambeat.domain.uri.Uri;

import java.util.List;

public class AlchemyInformation {

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
