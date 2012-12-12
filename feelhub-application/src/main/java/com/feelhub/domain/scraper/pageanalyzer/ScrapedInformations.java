package com.feelhub.domain.scraper.pageanalyzer;

import java.util.*;

public class ScrapedInformations {

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public void add(final String name, final String value) {
        scrapedTags.put(name, value);
    }

    public String getIllustration() {
        if (notEmpty(scrapedTags.get("opengraphimage"))) {
            return scrapedTags.get("opengraphimage");
        } else {
            if (PageAnalyzer.isFirstLevelUri(uri)) {
                return getIllustrationForFirstLevelDomain();
            } else {
                return getIllustrationForNonFirstLevelDomain();
            }
        }
    }

    private String getIllustrationForFirstLevelDomain() {
        if (notEmpty(scrapedTags.get("logo"))) {
            return scrapedTags.get("logo");
        } else if (notEmpty(scrapedTags.get("image"))) {
            return scrapedTags.get("image");
        }
        return "";
    }

    private String getIllustrationForNonFirstLevelDomain() {
        if (notEmpty(scrapedTags.get("image"))) {
            return scrapedTags.get("image");
        } else if (notEmpty(scrapedTags.get("logo"))) {
            return scrapedTags.get("logo");
        }
        return "";
    }

    public String getType() {
        if (notEmpty(scrapedTags.get("opengraphtype"))) {
            return scrapedTags.get("opengraphtype");
        } else {
            return "";
        }
    }

    public String getTitle() {
        if (notEmpty(scrapedTags.get("title"))) {
            return scrapedTags.get("title");
        } else {
            return "";
        }
    }

    private boolean notEmpty(final String tag) {
        return !tag.isEmpty();
    }

    private String uri;
    protected Map<String, String> scrapedTags = new HashMap<String, String>();
    private String title;
}
