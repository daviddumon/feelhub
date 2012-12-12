package com.feelhub.domain.scraper.pageanalyzer.extractors;

import org.jsoup.nodes.*;

public class OpenGraphTitleExtractor extends Extractor {

    @Override
    public String getName() {
        return "opengraphtitle";
    }

    @Override
    public String apply(final Document document) {
        final Element element = document.select("[property=og:title]").first();
        if (element == null) {
            return "";
        } else {
            return element.attr("content");
        }
    }
}
