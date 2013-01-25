package com.feelhub.domain.scraper;

import org.jsoup.nodes.*;

class JsoupTagExtractor {

    String parse(final Document document, final String tag) {
        final Element element = document.select(tag).first();
        if (element != null) {
            return extractTextFrom(element);
        }
        return "";
    }

    private String extractTextFrom(final Element element) {
        if (!element.ownText().isEmpty()) {
            return element.ownText();
        } else {
            for (final Element child : element.children()) {
                if (!child.ownText().isEmpty()) {
                    return child.ownText();
                }
            }
        }
        return "";
    }
}
