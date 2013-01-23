package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.google.inject.Inject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Scraper {

    @Inject
    public Scraper(final JsoupTitleExtractor jsoupTitleExtractor, final JsoupTagExtractor jsoupTagExtractor, final JsoupAttributExtractor jsoupAttributExtractor) {
        this.jsoupTitleExtractor = jsoupTitleExtractor;
        this.jsoupTagExtractor = jsoupTagExtractor;
        this.jsoupAttributExtractor = jsoupAttributExtractor;
    }

    public ScrapedInformation scrap(final String uri) {
        final ScrapedInformation scrapedInformation = new ScrapedInformation();
        final Document document = getDocument(uri);
        scrapLanguage(document, scrapedInformation);
        scrapDescription(document, scrapedInformation);
        scrapPersons(document, scrapedInformation);
        scrapName(document, scrapedInformation);
        return scrapedInformation;
    }

    private void scrapLanguage(final Document document, final ScrapedInformation scrapedInformation) {
        scrapedInformation.addLanguage(10, FeelhubLanguage.fromCode(jsoupAttributExtractor.parse(document, "http-equiv[Content-Language]", "content")));
        scrapedInformation.addLanguage(20, FeelhubLanguage.fromCode(jsoupAttributExtractor.parse(document, "html", "lang")));
    }

    private void scrapDescription(final Document document, final ScrapedInformation scrapedInformation) {
        scrapedInformation.addDescription(100, jsoupAttributExtractor.parse(document, "meta[name=description]", "content"));
        scrapedInformation.addDescription(90, jsoupAttributExtractor.parse(document, "meta[name=subject]", "content"));
        scrapedInformation.addDescription(80, jsoupAttributExtractor.parse(document, "meta[name=topic]", "content"));
        scrapedInformation.addDescription(70, jsoupAttributExtractor.parse(document, "meta[name=summary]", "content"));
    }

    private void scrapPersons(final Document document, final ScrapedInformation scrapedInformation) {
        final String author = jsoupAttributExtractor.parse(document, "meta[name=author]", "content");
        if (!author.isEmpty()) {
            scrapedInformation.addPerson(author);
        }
        final String designer = jsoupAttributExtractor.parse(document, "meta[name=designer]", "content");
        if (!designer.isEmpty()) {
            scrapedInformation.addPerson(designer);
        }
        final String owner = jsoupAttributExtractor.parse(document, "meta[name=owner]", "content");
        if (!owner.isEmpty()) {
            scrapedInformation.addPerson(owner);
        }
    }

    private void scrapName(final Document document, final ScrapedInformation scrapedInformation) {
        scrapedInformation.addName(100, jsoupAttributExtractor.parse(document, "meta[property=og:title]", "content"));
        scrapedInformation.addName(90, jsoupTitleExtractor.parse(document));
        scrapedInformation.addName(80, jsoupTagExtractor.parse(document, "h1"));
        scrapedInformation.addName(70, jsoupTagExtractor.parse(document, "h2"));
        scrapedInformation.addName(60, jsoupTagExtractor.parse(document, "h3"));
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent(USER_AGENT).timeout(THREE_SECONDS).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new Document("");
        }
    }

    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.7 (KHTML, like Gecko) Chrome/16.0.912.77 Safari/535.7";
    private final static int THREE_SECONDS = 3000;
    private JsoupTitleExtractor jsoupTitleExtractor;
    private JsoupTagExtractor jsoupTagExtractor;
    private JsoupAttributExtractor jsoupAttributExtractor;
}
