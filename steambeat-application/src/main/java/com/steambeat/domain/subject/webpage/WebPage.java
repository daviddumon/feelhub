package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.scrapers.WebPageScraper;
import com.steambeat.domain.subject.Subject;

import java.util.HashMap;

public class WebPage extends Subject {

    protected WebPage() {
    }

    public WebPage(final Association association, final WebPageScraper webPageScraper) {
        super(association.getCanonicalUri());
        scrapedTags = webPageScraper.getScrapedTags();

        //todo delete this code when mongolink handle maps
        titleTag = scrapedTags.get("title");
        h1Tag = scrapedTags.get("h1");
        h2Tag = scrapedTags.get("h2");
        imageUrlTag = scrapedTags.get("image");
        logoUrlTag = scrapedTags.get("logo");
    }

    public Uri getRealUri() {
        return new Uri(getId());
    }

    @Override
    protected String getTitle() {
        String title = "";
        if (!titleTag.isEmpty() && (titleTag.length() <= 100)) {
            title = titleTag;
        } else if (!h1Tag.isEmpty() && (h1Tag.length() <= 100)) {
            title = h1Tag;
        } else if (!h2Tag.isEmpty() && (h2Tag.length() <= 100)) {
            title = h2Tag;
        }
        return title;
        //String title = "";
        //        if (!scrapedTags.get("title").isEmpty() && (scrapedTags.get("title").length() <= 100)) {
        //            title = scrapedTags.get("title");
        //        } else if (!scrapedTags.get("h1").isEmpty() && (scrapedTags.get("h1").length() <= 100)) {
        //            title = scrapedTags.get("h1");
        //        } else if (!scrapedTags.get("h2").isEmpty() && (scrapedTags.get("h2").length() <= 100)) {
        //            title = scrapedTags.get("h2");
        //        } else if (!scrapedTags.get("h3").isEmpty() && (scrapedTags.get("h3").length() <= 100)) {
        //            title = scrapedTags.get("h3");
        //        }
        //        return title;
    }

    @Override
    protected String getShortTitle() {
        String title = "";
        if (!titleTag.isEmpty() && (titleTag.length() <= 30)) {
            title = titleTag;
        } else if (!h1Tag.isEmpty() && (h1Tag.length() <= 30)) {
            title = h1Tag;
        } else if (!h2Tag.isEmpty() && (h2Tag.length() <= 30)) {
            title = h2Tag;
        } else {
            title = extractShortTitleFromUri();
        }
        return title;
        //String shortTitle = "";
        //if (!scrapedTags.get("title").isEmpty() && (scrapedTags.get("title").length() <= 30)) {
        //    shortTitle = scrapedTags.get("title");
        //} else if (!scrapedTags.get("h1").isEmpty() && (scrapedTags.get("h1").length() <= 30)) {
        //    shortTitle = scrapedTags.get("h1");
        //} else if (!scrapedTags.get("h2").isEmpty() && (scrapedTags.get("h2").length() <= 30)) {
        //    shortTitle = scrapedTags.get("h2");
        //} else if (!scrapedTags.get("h3").isEmpty() && (scrapedTags.get("h3").length() <= 30)) {
        //    shortTitle = scrapedTags.get("h3");
        //} else {
        //    shortTitle = extractShortTitleFromUri();
        //}
        //return shortTitle;
    }

    private String extractShortTitleFromUri() {
        if (getRealUri().toString().length() < 30) {
            return getRealUri().toString();
        } else {
            final String domain = getRealUri().getDomain();
            final String end = getId().substring(getId().length() - 15, getId().length());
            return domain + " ... " + end;
        }
    }

    @Override
    protected String getThumbnailUrl() {
        String url = "";
        if (!logoUrlTag.isEmpty()) {
            url = logoUrlTag;
        } else {
            url = imageUrlTag;
        }
        return url;
        //String url = "";
        //        if (!scrapedTags.get("logoUrl").isEmpty()) {
        //            url = scrapedTags.get("logoUrl");
        //        } else {
        //            url = scrapedTags.get("firstImageUrl");
        //        }
        //        return url;
    }

    public HashMap<String, String> getScrapedTags() {
        return scrapedTags;
    }

    public String getTitleTag() {
        return titleTag;
    }

    public String getH1Tag() {
        return h1Tag;
    }

    public String getH2Tag() {
        return h2Tag;
    }

    public String getImageUrlTag() {
        return imageUrlTag;
    }

    public String getLogoUrlTag() {
        return logoUrlTag;
    }

    private HashMap<String, String> scrapedTags;

    //todo delete those fields when mongolink can handle maps
    private String titleTag = "";
    private String h1Tag = "";
    private String h2Tag = "";
    private String imageUrlTag = "";
    private String logoUrlTag = "";
}
