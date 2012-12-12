package com.feelhub.domain.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Scraper {

    public Document scrap(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent(USER_AGENT).timeout(THREE_SECONDS).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new Document("");
        }
    }

    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
    private final static int THREE_SECONDS = 3000;
}
