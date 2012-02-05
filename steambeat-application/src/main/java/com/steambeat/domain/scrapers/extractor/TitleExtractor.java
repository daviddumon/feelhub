package com.steambeat.domain.scrapers.extractor;

import org.jsoup.nodes.Document;

public class TitleExtractor extends Extractor {

    @Override
    public String getName() {
        return "title";
    }

    @Override
    public String apply(final Document document) {
        return document.title();
    }
}
