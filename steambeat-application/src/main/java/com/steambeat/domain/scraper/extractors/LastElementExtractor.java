package com.steambeat.domain.scraper.extractors;

import org.jsoup.nodes.*;

public class LastElementExtractor extends Extractor {

    public LastElementExtractor(final String name, final String tag) {
        this.name = name;
        this.tag = tag;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String apply(final Document document) {
        final Element element = document.select(tag).last();
        if (element != null) {
            return extractTextFrom(element);
        }
        return "";
    }

    private final String name;
    private final String tag;
}
