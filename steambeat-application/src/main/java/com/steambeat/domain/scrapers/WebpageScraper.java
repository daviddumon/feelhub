package com.steambeat.domain.scrapers;

import com.steambeat.domain.subject.webpage.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import java.io.IOException;
import java.util.HashMap;

public class WebPageScraper {

    //todo : extract rules of matching
    // index position of logo
    // bug :http://www.lemonde.fr/proche-orient/article/2012/02/04/a-homs-des-temoins-parlent-d-un-des-plus-grands-massacres-depuis-le-debut-du-soulevement_1639043_3218.html#ens_id=1481132
    // trouve pas h1 ... nested h1
    // http://le10sport.com/football/france/ligue1/om-l-episode-que-n-a-jamais-encaisse-lucho58156
    // pas de h1 et image avant h2

    public void scrapDocument(final Uri uri) {
        try {
            document = Jsoup.connect(uri.toString()).userAgent(userAgent).timeout(THREE_SECONDS).get();
        } catch (IOException e) {
            document = new Document("");
        } finally {
            scrapInterestingTagsFromDocument();
        }
    }

    private void scrapInterestingTagsFromDocument() {
        scrapedTags.put("title", document.title());
        scrapedTags.put("h1", scrapLastTag("h1"));
        scrapedTags.put("h2", scrapFirstTag("h2"));
        scrapedTags.put("logo", scrapLogo());
        scrapedTags.put("image", scrapImage());
    }

    private String scrapFirstTag(final String tag) {
        final Element element = document.select(tag).first();
        if (element != null) {
            return element.ownText();
        }
        return "";
    }

    private String scrapLastTag(final String tag) {
        final Element element = document.select(tag).last();
        if (element != null) {
            return element.ownText();
        }
        return "";
    }

    private String scrapLogo() {
        return extractImageFrom(findLogoElement());
    }

    private String extractImageFrom(final Element element) {
        if (element.hasAttr("src")) {
            return element.absUrl("src");
        } else if (element.hasAttr("style")) {
            final String styleAttribute = element.attr("style");
            return styleAttribute.substring(styleAttribute.indexOf("http://"), styleAttribute.indexOf(")"));
        }
        return "";
    }

    private Element findLogoElement() {
        final String selector = "[class~=(.*)(logo|Logo|LOGO)(.*)], [id~=(.*)(logo|Logo|LOGO)(.*)], [alt~=(.*)(logo|Logo|LOGO)(.*)]";
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
            Element child = element.children().get(i);
            if (child.nodeName().equals("img")) {
                return child;
            }
        }
        return new EmptyElement();
    }

    private String scrapImage() {
        final Element h1 = document.select("h1").first();
        if (h1 != null) {
            Element next = h1.nextElementSibling();
            while (next != null) {
                final Element image = findImageIn(next);
                if (image != null) {
                    return extractImageFrom(image);
                }
                next = next.nextElementSibling();
            }
        }
        return "";
    }

    private Element findImageIn(final Element element) {
        if (element.nodeName().equals("img")) {
            return element;
        } else if (element.children().size() > 0) {
            for (int i = 0; i < element.children().size(); i++) {
                final Element image = findImageIn(element.children().get(i));
                if (image != null) {
                    return image;
                }
            }
        }
        return null;
    }

    public HashMap<String, String> getScrapedTags() {
        return scrapedTags;
    }

    private Document document;
    protected HashMap<String, String> scrapedTags = new HashMap<String, String>();
    private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
    private final int THREE_SECONDS = 3000;
}
