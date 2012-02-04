package com.steambeat.domain.scrapers;

import com.steambeat.domain.subject.webpage.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import java.io.IOException;
import java.util.HashMap;

public class WebPageScraper {

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
        scrapedTags.put("h1", scrapFirstTag("h1"));
        scrapedTags.put("h2", scrapFirstTag("h2"));
        scrapedTags.put("logo", scrapLogo());
        scrapedTags.put("image", scrapImage());
    }

    private String scrapImage() {
        final Element h1 = document.select("h1").first();
        final Element nestedImage = findNestedImage(h1);
        return extractImageFrom(nestedImage);
    }

    private String scrapFirstTag(final String tag) {
        final Element element = document.select(tag).first();
        if (element != null) {
            return element.ownText();
        }
        return "";
    }

    private String scrapLogo() {
        return extractImageFrom(findLogoElement());
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


    private String extractImageFrom(final Element element) {
        if (element.hasAttr("src")) {
            return element.absUrl("src");
        } else if (element.hasAttr("style")) {
            final String styleAttribute = element.attr("style");
            return styleAttribute.substring(styleAttribute.indexOf("http://"), styleAttribute.indexOf(")"));
        }
        return "";
    }

    public HashMap<String, String> getScrapedTags() {
        return scrapedTags;
    }

    private Document document;
    protected HashMap<String, String> scrapedTags = new HashMap<String, String>();
    private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
    private final int THREE_SECONDS = 3000;
}
