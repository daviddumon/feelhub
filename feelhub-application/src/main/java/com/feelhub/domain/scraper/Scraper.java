package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.uri.Uri;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class Scraper {

    public ScrapedInformation scrap(final String uri) {
        final ScrapedInformation scrapedInformation = new ScrapedInformation();
        final Document document = getDocument(uri);
        scrapType(document, scrapedInformation);
        scrapLanguage(document, scrapedInformation);
        scrapDescription(document, scrapedInformation);
        scrapPersons(document, scrapedInformation);
        scrapName(document, scrapedInformation);
        scrapImages(document, scrapedInformation, uri);
        scrapVideos(document, scrapedInformation);
        scrapAudios(document, scrapedInformation);
        return scrapedInformation;
    }

    private void scrapType(final Document document, final ScrapedInformation scrapedInformation) {
        scrapedInformation.addType(jsoupAttributExtractor.parse(document, "meta[property=og:type]", "content"));
    }

    private void scrapLanguage(final Document document, final ScrapedInformation scrapedInformation) {
        scrapedInformation.addLanguage(20, FeelhubLanguage.fromCode(jsoupAttributExtractor.parse(document, "html", "lang")));
        scrapedInformation.addLanguage(10, FeelhubLanguage.fromCode(jsoupAttributExtractor.parse(document, "http-equiv[Content-Language]", "content")));
    }

    private void scrapDescription(final Document document, final ScrapedInformation scrapedInformation) {
        scrapedInformation.addDescription(50, jsoupAttributExtractor.parse(document, "meta[property=og:description]", "content"));
        scrapedInformation.addDescription(40, jsoupAttributExtractor.parse(document, "meta[name=description]", "content"));
        scrapedInformation.addDescription(30, jsoupAttributExtractor.parse(document, "meta[name=subject]", "content"));
        scrapedInformation.addDescription(20, jsoupAttributExtractor.parse(document, "meta[name=topic]", "content"));
        scrapedInformation.addDescription(10, jsoupAttributExtractor.parse(document, "meta[name=summary]", "content"));
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
        scrapedInformation.addName(50, jsoupAttributExtractor.parse(document, "meta[property=og:title]", "content"));
        scrapedInformation.addName(40, jsoupTitleExtractor.parse(document));
        scrapedInformation.addName(30, jsoupTagExtractor.parse(document, "h1"));
        scrapedInformation.addName(20, jsoupTagExtractor.parse(document, "h2"));
        scrapedInformation.addName(10, jsoupTagExtractor.parse(document, "h3"));
    }

    private void scrapImages(final Document document, final ScrapedInformation scrapedInformation, final String uriToScrap) {
        final List<String> images = jsoupGroupAttributExtractor.parse(document, "meta[property=og:image]", "content");
        for (final String image : images) {
            if (!image.isEmpty()) {
                final Uri uri = new Uri(image);
                if (uri.getDomain().isEmpty()) {
                    final String rootProtocol = new Uri(uriToScrap).getCorrectProtocol();
                    final String rootDomain = new Uri(uriToScrap).getDomain();
                    scrapedInformation.addImage(rootProtocol + rootDomain + uri.getDomainAndAddressOnly());
                } else {
                    scrapedInformation.addImage(uri.getCorrectProtocol() + uri.getDomainAndAddressOnly());
                }
            }
        }
    }

    private void scrapVideos(final Document document, final ScrapedInformation scrapedInformation) {
        final List<String> videos = jsoupGroupAttributExtractor.parse(document, "meta[property=og:video]", "content");
        for (final String video : videos) {
            scrapedInformation.addVideo(video);
        }
    }

    private void scrapAudios(final Document document, final ScrapedInformation scrapedInformation) {
        final List<String> audios = jsoupGroupAttributExtractor.parse(document, "meta[property=og:video]", "content");
        for (final String audio : audios) {
            scrapedInformation.addAudio(audio);
        }
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent(USER_AGENT).timeout(THREE_SECONDS).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new Document("");
        }
    }

    private final static String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36";
    private final static int THREE_SECONDS = 3000;
    private JsoupTitleExtractor jsoupTitleExtractor = new JsoupTitleExtractor();
    private JsoupTagExtractor jsoupTagExtractor = new JsoupTagExtractor();
    private JsoupAttributExtractor jsoupAttributExtractor = new JsoupAttributExtractor();
    private JsoupGroupAttributExtractor jsoupGroupAttributExtractor = new JsoupGroupAttributExtractor();
}
