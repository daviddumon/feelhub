package com.steambeat.domain.scraper.extractors;

import com.steambeat.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsLogoExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        logoExtractor = new LogoExtractor("logo", "");
    }

    @Test
    public void canGetName() {
        assertThat(logoExtractor.getName(), is("logo"));
    }

    @Test
    public void canFindLogoWithClass() {
        final String uri = internet.uri("logoextractor/withclasslogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithId() {
        final String uri = internet.uri("logoextractor/withidlogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAlt() {
        final String uri = internet.uri("logoextractor/withaltlogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithClassPattern() {
        final String uri = internet.uri("logoextractor/withclasslogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithIdPattern() {
        final String uri = internet.uri("logoextractor/withidlogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindImageLogoWithAltPattern() {
        final String uri = internet.uri("logoextractor/withaltlogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindBackgroundImageUrl() {
        final String uri = internet.uri("logoextractor/backgroundimage");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/image.jpg"));
    }

    @Test
    public void canFindLogoFromNestedElement() {
        final String uri = internet.uri("logoextractor/logofromnested");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.google.fr/images/lol.jpg"));
    }

    @Test
    public void canFindLogoBugTironFr() {
        final String uri = internet.uri("logoextractor/bug/tironfr");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://localhost:6162/sites/default/files/tiron_logo.png"));
    }

    @Test
    public void canFindLogoFromCSS() {
        final String uri = internet.uri("logoextractor/fromcss");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("good.png"));
    }

    @Test
    public void canExtractFromBannerPattern() {
        final String uri = internet.uri("logoextractor/withbannerpattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is("http://www.image.com/good.jpg"));
    }

    @Test
    public void canExtractFromDomainWithoutTLDPattern() {
        final String uri = internet.uri("logoextractor/withoutTLD");
        final Document document = getDocument(uri);
        final LogoExtractor extractorWithoutTLD = new LogoExtractor("logo", "localhost");

        final String result = extractorWithoutTLD.apply(document);

        assertThat(result, is("http://www.image.com/good.jpg"));
    }

    @Test
    public void io9bug() {
        final String uri = internet.uri("logoextractor/io9bug");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result, is(""));
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private LogoExtractor logoExtractor;

}