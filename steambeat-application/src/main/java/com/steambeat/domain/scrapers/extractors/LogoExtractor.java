package com.steambeat.domain.scrapers.extractors;

import com.steambeat.domain.scrapers.EmptyElement;
import org.jsoup.nodes.*;

public class LogoExtractor extends Extractor {

    public LogoExtractor(final String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String apply(final Document document) {
        return extractImageFrom(findLogoElement(document));
    }

    private Element findLogoElement(final Document document) {
        final Element element = document.select(selector).first();
        if (element != null) {
            if (element.nodeName().equals("img")) {
                return element;
            } else {
                return findNestedImage(element);
            }
        }
        return new EmptyElement();
    }

    private Element findNestedImage(final Element element) {
        for (int i = 0; i < element.children().size(); i++) {
            final Element image = findImageIn(element.children().get(i));
            if (image != null) {
                return image;
            }
        }
        return new EmptyElement();
    }

    private String name;
    private final String selector = "[class~=(.*)(logo|Logo|LOGO)(.*)], [id~=(.*)(logo|Logo|LOGO)(.*)], [alt~=(.*)(logo|Logo|LOGO)(.*)]";
}
