package com.steambeat.domain.scrapers;

import com.steambeat.domain.subject.webpage.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

import java.io.IOException;
import java.util.HashMap;

public class WebPageScraper {

    public void scrapDocument(final Uri uri) {
        try {
            document = Jsoup.connect(uri.toString())
                    .userAgent(userAgent)
                    .timeout(3000).get();
            scrapInterestingTagsFromDocument();
        } catch (IOException e) {
            throw new WebScraperException();
        }
    }

    private void scrapInterestingTagsFromDocument() {
        scrapedTags.put("title", scrapTitle());
        scrapedTags.put("h1", scrapFirstTag("h1"));
        scrapedTags.put("h2", scrapFirstTag("h2"));
        scrapedTags.put("h3", scrapFirstTag("h3"));
        scrapedTags.put("firstImageUrl", scrapFirstImage());
        scrapedTags.put("logoUrl", scrapLogo());
    }

    private String scrapTitle() {
        return document.title();
    }

    private String scrapFirstTag(final String tag) {
        final Element element = document.select(tag).first();
        if (element != null) {
            return element.ownText();
        }
        return "";
    }

    private String scrapFirstImage() {
        final Element img = document.select("img").first();
        if (img != null) {
            return img.absUrl("src");
        }
        return "";
    }

    private String scrapLogo() {
        final String selector = "[class~=(.*)(logo|Logo|LOGO)(.*)], [id~=(.*)(logo|Logo|LOGO)(.*)], [alt~=(.*)(logo|Logo|LOGO)(.*)]";
        final Element element = document.select(selector).first();
        if (element != null) {
            final String logo = extractLogoFor(element);
            if (logo.isEmpty()) {
                return extractFromNested(element);
            }
            return logo;
        }
        return "";
    }

    private String extractLogoFor(final Element element) {
        final String srcUrl = element.absUrl("src");
        if (srcUrl.isEmpty()) {
            return extractLogoFromBackgroundImage(element);
        }
        return srcUrl;
    }

    private String extractLogoFromBackgroundImage(final Element element) {
        final String attributeKey = "style";
        if (element.hasAttr(attributeKey)) {
            final String styleAttr = element.attr(attributeKey);
            return styleAttr.substring(styleAttr.indexOf("http://"), styleAttr.indexOf(")"));
        }
        return "";
    }

    private String extractFromNested(final Element element) {
        String logo = extractFromChildren(element);
        if (logo.isEmpty()) {
            logo = extractFromParents(element);
        }
        return logo;
    }

    private String extractFromChildren(final Element element) {
        for (int i = 0; i < element.children().size(); i++) {
            Element child = element.children().get(i);
            final String childImage = extractLogoFor(child);
            if (!childImage.isEmpty()) {
                return childImage;
            }
        }
        return "";
    }

    private String extractFromParents(final Element element) {
        for (int i = 0; i < element.parents().size(); i++) {
            Element parent = element.parents().get(i);
            final String logo = extractLogoFor(parent);
            if (!logo.isEmpty()) {
                return logo;
            }
        }
        return "";
    }

    public HashMap<String, String> getScrapedTags() {
        return scrapedTags;
    }

    private HashMap<String, String> scrapedTags = new HashMap<String, String>();
    private Document document;
    private String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
}
