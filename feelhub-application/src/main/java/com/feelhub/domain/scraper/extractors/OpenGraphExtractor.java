package com.feelhub.domain.scraper.extractors;

import org.jsoup.nodes.*;

public class OpenGraphExtractor extends Extractor {

    @Override
    public String getName() {
        return "opengraph";
    }

    @Override
    public String apply(final Document document) {
        final Element element = document.select("[property=og:image]").first();
        if (element == null) {
            return "";
        } else {
            return element.attr("content");
        }
    }
}
