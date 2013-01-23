package com.feelhub.domain.scraper;

import org.jsoup.nodes.*;

public class JsoupAttributExtractor {

    String parse(final Document document, final String tag, final String attribut) {
        final Element element = document.select(tag).first();
        if (element != null) {
            return element.attr(attribut);
        }
        return "";
    }
}
