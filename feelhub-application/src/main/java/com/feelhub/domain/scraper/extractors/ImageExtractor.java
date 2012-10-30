package com.feelhub.domain.scraper.extractors;

import org.jsoup.nodes.*;

public class ImageExtractor extends Extractor {

    public ImageExtractor(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String apply(final Document document) {
        String imageUrl = "";
        final Element heading = getRelevantHeading(document);
        if (heading != null) {
            imageUrl = extractAfterHeading(heading);
            if (imageUrl.isEmpty()) {
                imageUrl = extractBeforeHeading(heading);
            }
            if (imageUrl.isEmpty()) {
                imageUrl = extractAfterHeading(heading.parent());
            }
            if (imageUrl.isEmpty()) {
                imageUrl = extractBeforeHeading(heading.parent());
            }
        }
        return imageUrl;
    }

    private Element getRelevantHeading(final Document document) {
        final Element h1 = document.select("h1").last();
        if (h1 != null) {
            return h1;
        } else {
            final Element h2 = document.select("h2").first();
            if (h2 != null) {
                return h2;
            }
        }
        return null;
    }

    private String extractAfterHeading(final Element heading) {
        Element next = heading.nextElementSibling();
        int depth = 5;
        while (next != null && depth > 0) {
            final Element image = findImageIn(next);
            if (image != null) {
                return extractImageFrom(image);
            }
            next = next.nextElementSibling();
            depth--;
        }
        return "";
    }

    private String extractBeforeHeading(final Element heading) {
        Element previous = heading.previousElementSibling();
        int depth = 5;
        while (previous != null && depth > 0) {
            final Element image = findImageIn(previous);
            if (image != null) {
                return extractImageFrom(image);
            }
            previous = previous.previousElementSibling();
            depth--;
        }
        return "";
    }

    private final String name;
}
