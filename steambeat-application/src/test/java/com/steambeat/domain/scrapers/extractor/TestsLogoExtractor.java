package com.steambeat.domain.scrapers.extractor;

import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsLogoExtractor {

    @Rule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        logoExtractor = new LogoExtractor("logo");
    }

    @AfterClass
    public static void afterClass() {
        internet.stop();
    }

    @Test
    public void canGetName() {
        assertThat(logoExtractor.getName(), is("logo"));
    }

    @Test
    public void canFindLogoWithClass() {
        final Uri uri = internet.uri("http://webscraper/logo/withclasslogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithId() {
        final Uri uri = internet.uri("http://webscraper/logo/withidlogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAlt() {
        final Uri uri = internet.uri("http://webscraper/logo/withaltlogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithClassPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/withclasslogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithIdPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/withidlogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAltPattern() {
        final Uri uri = internet.uri("http://webscraper/logo/withaltlogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindBackgroundImageUrl() {
        final Uri uri = internet.uri("http://webscraper/logo/backgroundimage");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindLogoFromNestedElement() {
        final Uri uri = internet.uri("http://webscraper/logo/logofromnested");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.google.fr/images/lol.jpg"));
    }

    @Test
    public void canFindLogoBugTironFr() {
        final Uri uri = internet.uri("http://webscraper/bug/tironfr");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://localhost:6162/sites/default/files/tiron_logo.png"));
    }

    private Document getDocument(final Uri uri) {
        try {
            return Jsoup.connect(uri.toString()).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private LogoExtractor logoExtractor;

}
