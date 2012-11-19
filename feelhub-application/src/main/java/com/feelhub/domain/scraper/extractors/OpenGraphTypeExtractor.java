package com.feelhub.domain.scraper.extractors;

import org.jsoup.nodes.*;

public class OpenGraphTypeExtractor extends Extractor {

    @Override
    public String getName() {
        return "opengraphtype";
    }

    @Override
    public String apply(final Document document) {
        final Element element = document.select("[property=og:type]").first();
        if (element == null) {
            return "";
        } else {
            return element.attr("content");
        }
    }
}
