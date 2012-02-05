package com.steambeat.domain.scrapers;

import com.google.common.collect.Lists;
import com.steambeat.domain.scrapers.extractor.Extractor;
import com.steambeat.domain.subject.webpage.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

public class WebPageScraper {

    public void addExtractor(final Extractor extractor) {
        extractors.add(extractor);
    }

    public void scrap(final Uri uri) {
        getDocument(uri);
        useExtractors();
    }

    private void getDocument(final Uri uri) {
        try {
            document = Jsoup.connect(uri.toString()).userAgent(userAgent).timeout(THREE_SECONDS).get();
        } catch (IOException e) {
            document = new Document("");
        }
    }

    private void useExtractors() {
        for (Extractor extractor : extractors) {
            scrapedTags.put(extractor.getName(), extractor.apply(document));
        }
    }

    public HashMap<String, String> getScrapedTags() {
        return scrapedTags;
    }

    private Document document;
    private List<Extractor> extractors = Lists.newArrayList();
    protected HashMap<String, String> scrapedTags = new HashMap<String, String>();
    private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
    private final int THREE_SECONDS = 3000;
}
