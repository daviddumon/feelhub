package com.feelhub.domain.scraper.extractors;

import org.jsoup.nodes.*;

public abstract class Extractor {

    public abstract String getName();

    public abstract String apply(final Document document);

    protected String extractImageFrom(final Element element) {
        if (element.hasAttr("src") && !element.attr("src").isEmpty()) {
            return element.absUrl("src");
        } else if (element.hasAttr("style")) {
            final String styleAttribute = element.attr("style");
            if (styleAttribute.contains("background-image")) {
                if (styleAttribute.contains("display: none")) {
                    return "";
                } else {
                    return styleAttribute.substring(styleAttribute.indexOf("url(") + 4, styleAttribute.indexOf(")"));
                }
            } else {
                return "";
            }
        }
        return "";
    }

    protected String extractTextFrom(final Element element) {
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

    protected Element findImageIn(final Element element) {
        if (element.nodeName().equals("img")) {
            return element;
        } else if (!element.children().isEmpty()) {
            for (final Element child : element.children()) {
                final Element image = findImageIn(child);
                if (image != null) {
                    return image;
                }
            }
        }
        return null;
    }
}