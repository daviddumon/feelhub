package com.feelhub.domain.scraper.pageanalyzer.extractors;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsOpenGraphImageExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        openGraphImageExtractor = new OpenGraphImageExtractor();
    }

    @Test
    public void canGetName() {
        assertThat(openGraphImageExtractor.getName()).isEqualTo("opengraphimage");
    }

    @Test
    public void canFindOpenGraphIllustration() {
        final String uri = internet.uri("opengraphextractor/illustration");
        final Document document = getDocument(uri);

        final String result = openGraphImageExtractor.apply(document);

        assertThat(result).isEqualTo("http://i4.ytimg.com/vi/_IOqGclele4/mqdefault.jpg");
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private OpenGraphImageExtractor openGraphImageExtractor;
}
