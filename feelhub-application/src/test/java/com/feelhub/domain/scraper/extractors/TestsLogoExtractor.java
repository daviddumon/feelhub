package com.feelhub.domain.scraper.extractors;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsLogoExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        logoExtractor = new LogoExtractor();
    }

    @Test
    public void canGetName() {
        assertThat(logoExtractor.getName()).isEqualTo("logo");
    }

    @Test
    public void canFindLogoWithClass() {
        final String uri = internet.uri("logoextractor/withclasslogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://www.image.com/image.jpg");
    }

    @Test
    public void canFindImageLogoWithId() {
        final String uri = internet.uri("logoextractor/withidlogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://www.image.com/image.jpg");
    }

    @Test
    public void canFindImageLogoWithAlt() {
        final String uri = internet.uri("logoextractor/withaltlogo");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://www.image.com/image.jpg");
    }

    @Test
    public void canFindImageLogoWithClassPattern() {
        final String uri = internet.uri("logoextractor/withclasslogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://www.image.com/image.jpg");
    }

    @Test
    public void canFindImageLogoWithIdPattern() {
        final String uri = internet.uri("logoextractor/withidlogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://www.image.com/image.jpg");
    }

    @Test
    public void canFindImageLogoWithAltPattern() {
        final String uri = internet.uri("logoextractor/withaltlogopattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://www.image.com/image.jpg");
    }

    @Test
    public void canFindBackgroundImageUrl() {
        final String uri = internet.uri("logoextractor/backgroundimage");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://www.image.com/image.jpg");
    }

    @Test
    public void canFindLogoFromNestedElement() {
        final String uri = internet.uri("logoextractor/logofromnested");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://www.google.fr/images/lol.jpg");
    }

    @Test
    public void canFindLogoBugTironFr() {
        final String uri = internet.uri("logoextractor/bug/tironfr");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://localhost:6162/sites/default/files/tiron_logo.png");
    }

    @Test
    public void canFindLogoFromCSS() {
        final String uri = internet.uri("logoextractor/fromcss");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("good.png");
    }

    @Test
    public void canExtractFromBannerPattern() {
        final String uri = internet.uri("logoextractor/withbannerpattern");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEqualTo("http://www.image.com/good.jpg");
    }

    @Test
    public void io9bug() {
        final String uri = internet.uri("logoextractor/io9bug");
        final Document document = getDocument(uri);

        final String result = logoExtractor.apply(document);

        assertThat(result).isEmpty();
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
