package com.steambeat.domain.scrapers;

import com.google.common.collect.Lists;
import com.steambeat.domain.scrapers.extractor.*;
import com.steambeat.domain.subject.webpage.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UriScraper implements Scraper {

    public UriScraper() {
        this.extractors.add(new TitleExtractor());
        this.extractors.add(new LastElementExtractor("h1", "h1"));
        this.extractors.add(new FirstElementExtractor("h2", "h2"));
        this.extractors.add(new LogoExtractor("logo"));
        this.extractors.add(new ImageExtractor("image"));
    }

    public void scrap(final Uri uri) {
        this.uri = uri;
        getDocument(uri);
        useExtractors();
    }

    private void getDocument(final Uri uri) {
        try {
            document = Jsoup.connect(uri.toString()).userAgent(USER_AGENT).timeout(THREE_SECONDS).get();
        } catch (IOException e) {
            document = new Document("");
        }
    }

    private void useExtractors() {
        for (Extractor extractor : extractors) {
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

    @Override
    public String getIllustration() {
        if (notEmpty(scrapedTags.get("logo"))) {
            return scrapedTags.get("logo");
        } else if (notEmpty(scrapedTags.get("image"))) {
            return scrapedTags.get("image");
        }
        return "";
    }

    private boolean notEmpty(final String tag) {
        return !tag.isEmpty();
    }

    private Document document;
    private List<Extractor> extractors = Lists.newArrayList();
    protected Map<String, String> scrapedTags = new HashMap<String, String>();
    private Uri uri;
    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
    private final static int THREE_SECONDS = 3000;
}
