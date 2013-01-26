package com.feelhub.domain.scraper;

import org.jsoup.nodes.Document;

public class JsoupTitleExtractor {

    String parse(final Document document) {
        return document.title();
    }
}
