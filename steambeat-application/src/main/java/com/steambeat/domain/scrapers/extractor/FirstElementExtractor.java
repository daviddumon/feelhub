package com.steambeat.domain.scrapers.extractor;

import org.jsoup.nodes.*;

public class FirstElementExtractor extends Extractor {

    public FirstElementExtractor(final String name, final String tag) {
        this.name = name;
        this.tag = tag;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String apply(final Document document) {
        final Element element = document.select(tag).first();
        if (element != null) {
            return extractTextFrom(element);
        }
        return "";
    }

    private String name;
    private String tag;
}
