package com.steambeat.domain.scrapers.extractors;

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
        final Uri uri = internet.uri("logoextractor/withclasslogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithId() {
        final Uri uri = internet.uri("logoextractor/withidlogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAlt() {
        final Uri uri = internet.uri("logoextractor/withaltlogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithClassPattern() {
        final Uri uri = internet.uri("logoextractor/withclasslogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithIdPattern() {
        final Uri uri = internet.uri("logoextractor/withidlogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAltPattern() {
        final Uri uri = internet.uri("logoextractor/withaltlogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindBackgroundImageUrl() {
        final Uri uri = internet.uri("logoextractor/backgroundimage");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindLogoFromNestedElement() {
        final Uri uri = internet.uri("logoextractor/logofromnested");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.google.fr/images/lol.jpg"));
    }

    @Test
    public void canFindLogoBugTironFr() {
        final Uri uri = internet.uri("logoextractor/bug/tironfr");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://localhost:6162/sites/default/files/tiron_logo.png"));
    }

    @Test
    public void canFindLogoFromCSS() {
        final Uri uri = internet.uri("logoextractor/fromcss");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("good.png"));
    }

    @Test
    public void canExtractFromBannerPattern() {
        final Uri uri = internet.uri("logoextractor/withbannerpattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/good.jpg"));
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
