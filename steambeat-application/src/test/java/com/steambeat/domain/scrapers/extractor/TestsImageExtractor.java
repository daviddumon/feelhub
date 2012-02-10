package com.steambeat.domain.scrapers.extractor;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsImageExtractor {

    @Rule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        imageExtractor = new ImageExtractor("image");
    }

    @AfterClass
    public static void afterClass() {
        internet.stop();
    }

    @Test
    public void canGetName() {
        assertThat(imageExtractor.getName(), is("image"));
    }

    @Test
    public void canFindRelevantImageAfterH1Tag() {
        final Uri uri = internet.uri("imageextractor/withH1tag");
        final Document document = getDocument(uri);

        final String result = imageExtractor.apply(document);

        assertThat(result, is("http://www.google.fr/images/h1.jpg"));
    }

    @Test
    public void slateFrBug() {
        final Uri uri = internet.uri("imageextractor/bug/slatefr");
        final Document document = getDocument(uri);

        final String result = imageExtractor.apply(document);

        assertThat(result, is("http://www.slate.fr/sites/default/files/sarkozy-tv_0.jpg?1328353776"));
    }

    @Test
    public void canFindRelevantImageBeforeH2Heading() {
        final Uri uri = internet.uri("imageextractor/bug/10sportbug");
        final Document document = getDocument(uri);

        final String result = imageExtractor.apply(document);

        assertThat(result, is("http://www.10sport.com/image.jpg"));
    }

    private Document getDocument(final Uri uri) {
        try {
            return Jsoup.connect(uri.toString()).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private ImageExtractor imageExtractor;
}
