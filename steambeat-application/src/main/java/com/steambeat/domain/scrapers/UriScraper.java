package com.steambeat.domain.scrapers;

import com.google.common.collect.Lists;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.scrapers.extractors.*;
import com.steambeat.domain.uri.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.*;

public class UriScraper implements Scraper {

    @Override
    public void scrap(final Keyword identifier) {
        this.uri = new Uri(identifier.getValue());
        addExtractors();
        getJSoupDocument();
        useExtractors();
    }

    private void addExtractors() {
        this.extractors.add(new TitleExtractor());
        this.extractors.add(new LastElementExtractor("h1", "h1"));
        this.extractors.add(new FirstElementExtractor("h2", "h2"));
        this.extractors.add(new LogoExtractor("logo", uri.withoutTLD()));
        this.extractors.add(new ImageExtractor("image"));
    }

    private void getJSoupDocument() {
        try {
            document = Jsoup.connect(uri.toString()).userAgent(USER_AGENT).timeout(THREE_SECONDS).get();
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

    @Override
    public String getShortDescription() {
        if (getDescription().length() < 40 && notEmpty(getDescription())) {
            return getDescription();
        } else {
            return uri.condensed();
        }
    }

    @Override
    public String getDescription() {
        if (notEmpty(scrapedTags.get("title"))) {
            return scrapedTags.get("title");
        } else if (notEmpty(scrapedTags.get("h1"))) {
            return scrapedTags.get("h1");
        } else if (notEmpty(scrapedTags.get("h2"))) {
            return scrapedTags.get("h2");
        }
        return uri.toString();
    }

    private boolean notEmpty(final String tag) {
        return !tag.isEmpty();
    }

    @Override
    public String getIllustration() {
        if (uri.isFirstLevelUri()) {
            return getIllustrationForFirstLevelDomain();
        } else {
            return getIllustrationForNonFirstLevelDomain();
        }
    }

    public String getIllustrationForFirstLevelDomain() {
        if (notEmpty(scrapedTags.get("logo"))) {
            return scrapedTags.get("logo");
        } else if (notEmpty(scrapedTags.get("image"))) {
            return scrapedTags.get("image");
        }
        return "";
    }

    public String getIllustrationForNonFirstLevelDomain() {
        if (notEmpty(scrapedTags.get("image"))) {
            return scrapedTags.get("image");
        } else if (notEmpty(scrapedTags.get("logo"))) {
            return scrapedTags.get("logo");
        }
        return "";
    }

    private Document document;
    private final List<Extractor> extractors = Lists.newArrayList();
    protected Map<String, String> scrapedTags = new HashMap<String, String>();
    private Uri uri;
    private Keyword identifier;
    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
    private final static int THREE_SECONDS = 3000;
}
