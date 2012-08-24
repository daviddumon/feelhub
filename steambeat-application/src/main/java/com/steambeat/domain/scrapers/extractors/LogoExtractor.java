package com.steambeat.domain.scrapers.extractors;

import com.google.common.collect.Lists;
import com.steambeat.domain.scrapers.EmptyElement;
import com.steambeat.domain.scrapers.miner.CSSMiner;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.util.List;

public class LogoExtractor extends Extractor {

    public LogoExtractor(final String name, final String domainWithoutTLD) {
        this.validTags = "logo|Logo|LOGO|banner|Banner|BANNER" + (notEmpty(domainWithoutTLD) ? "|" + domainWithoutTLD : "");
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String apply(final Document document) {
        final Element logoElement = findLogoElement(document);
        String logoUrl = extractImageFrom(logoElement);
        if (logoUrl.isEmpty()) {
            logoUrl = mineCss(document);
        }
        return logoUrl;
    }

    private Element findLogoElement(final Document document) {
        final String selector = "[class~=(.*)(" + validTags + ")(.*)], [id~=(.*)(" + validTags + ")(.*)], [alt~=(.*)(" + validTags + ")(.*)]";
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

    private String mineCss(final Document document) {
        final List<String> cssList = findCSSUris(document);
        String backgroundUrl = "";
        for (final String css : cssList) {
            final CSSMiner cssMiner = new CSSMiner(css);
            backgroundUrl = cssMiner.scrap("(logo|banner)");
            if (notEmpty(backgroundUrl)) {
                return backgroundUrl;
            }
        }
        return backgroundUrl;
    }

    private boolean notEmpty(final String backgroundUrl) {
        return !backgroundUrl.isEmpty();
    }

    private List<String> findCSSUris(final Document document) {
        final Elements links = document.select("link[rel=stylesheet]");
        final List<String> result = Lists.newArrayList();
        for (final Element link : links) {
            result.add(link.absUrl("href"));
        }
        return result;
    }

    private final String name;
    private final String validTags;
}
