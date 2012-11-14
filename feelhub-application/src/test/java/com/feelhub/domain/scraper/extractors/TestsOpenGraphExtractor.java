package com.feelhub.domain.scraper.extractors;

import com.feelhub.test.FakeInternet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpenGraphExtractor {

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        openGraphExtractor = new OpenGraphExtractor();
    }

    @Test
    public void canGetName() {
        assertThat(openGraphExtractor.getName(), is("opengraph"));
    }

    @Test
    public void canFindOpenGraphIllustration() {
        final String uri = internet.uri("opengraphextractor/illustration");
        final Document document = getDocument(uri);

        final String result = openGraphExtractor.apply(document);

        assertThat(result, is("http://i4.ytimg.com/vi/_IOqGclele4/mqdefault.jpg"));
    }

    private Document getDocument(final String uri) {
        try {
            return Jsoup.connect(uri).userAgent("").timeout(3000).get();
        } catch (IOException e) {
            return new Document("");
        }
    }

    private OpenGraphExtractor openGraphExtractor;
}
