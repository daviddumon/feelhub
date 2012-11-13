package com.feelhub.domain.scraper;

import com.feelhub.domain.scraper.extractors.*;
import com.google.common.collect.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;
import java.util.regex.*;

public class Scraper {

    public void scrap(final String uri) {
        this.uri = uri;

        // todo : enlever ce bloc une fois resolu le probleme de l'instantiation dans les singletons listeners d'events
        this.document = null;
        this.extractors = Lists.newArrayList();
        this.scrapedTags = Maps.newHashMap();
        //end

        addExtractors();
        getJSoupDocument();
        useExtractors();
    }

    private void addExtractors() {
        this.extractors.add(new LogoExtractor("logo", uri));
        this.extractors.add(new ImageExtractor("image"));
        this.extractors.add(new OpenGraphExtractor());
    }

    private void getJSoupDocument() {
        try {
            document = Jsoup.connect(uri).userAgent(USER_AGENT).timeout(THREE_SECONDS).get();
        } catch (Exception e) {
            e.printStackTrace();
            document = new Document("");
        }
    }

    private void useExtractors() {
        for (final Extractor extractor : extractors) {
            scrapedTags.put(extractor.getName(), extractor.apply(document));
        }
    }

    private boolean notEmpty(final String tag) {
        return !tag.isEmpty();
    }

    public String getIllustration() {
        if (notEmpty(scrapedTags.get("opengraph"))) {
            return scrapedTags.get("opengraph");
        } else {
            if (Scraper.isFirstLevelUri(uri)) {
                return getIllustrationForFirstLevelDomain();
            } else {
                return getIllustrationForNonFirstLevelDomain();
            }
        }
    }

    public static boolean isFirstLevelUri(final String uri) {
        final Pattern first_level_check = Pattern.compile(".*[a-zA-Z0-9]/.+$");
        final Matcher matcher = first_level_check.matcher(uri);
        if (matcher.matches()) {
            return false;
        }
        return true;
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

    private Document document;
    private List<Extractor> extractors = Lists.newArrayList();
    protected Map<String, String> scrapedTags = new HashMap<String, String>();
    private String uri;
    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
    private final static int THREE_SECONDS = 3000;
}
