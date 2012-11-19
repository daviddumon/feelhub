package com.feelhub.domain.scraper;

import com.feelhub.domain.scraper.extractors.*;
import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;
import java.util.regex.*;

public class Scraper {

    public Scraper() {
        addExtractors();
    }

    private void addExtractors() {
        this.extractors.add(new LogoExtractor());
        this.extractors.add(new ImageExtractor());
        this.extractors.add(new OpenGraphImageExtractor());
        this.extractors.add(new OpenGraphTitleExtractor());
        this.extractors.add(new OpenGraphTypeExtractor());
        this.extractors.add(new TitleExtractor());
    }

    public ScrapedInformations scrap(final String uri) {
        final ScrapedInformations scrapedInformations = new ScrapedInformations();
        scrapedInformations.setUri(uri);
        useExtractors(getJSoupDocument(uri), scrapedInformations);
        return scrapedInformations;
    }

    private Document getJSoupDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent(USER_AGENT).timeout(THREE_SECONDS).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new Document("");
        }
    }

    private void useExtractors(final Document document, final ScrapedInformations scrapedInformations) {
        for (final Extractor extractor : extractors) {
            scrapedInformations.add(extractor.getName(), extractor.apply(document));
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

    private List<Extractor> extractors = Lists.newArrayList();
    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
    private final static int THREE_SECONDS = 3000;
}
