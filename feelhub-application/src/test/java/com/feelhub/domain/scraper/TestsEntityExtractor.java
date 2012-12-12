package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.usable.web.*;
import com.feelhub.test.FakeInternet;
import org.apache.commons.lang.WordUtils;
import org.jsoup.nodes.Document;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;
@Ignore
public class TestsEntityExtractor {

    private EntityExtractor entityExtractor;
    private FeelhubLanguage language;
    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        entityExtractor = new EntityExtractor();
        language = FeelhubLanguage.reference();
    }

    @Test
    public void canExtractEntitiesFromEmptyDocument() {
        final Document document = getDocument("scraper/empty");

        final List<WebTopic> topics = entityExtractor.analyze(document, language);

        assertThat(topics).isNotEmpty();
        final WebTopic webTopic = topics.get(0);
        assertThat(webTopic.getType()).isEqualTo(WebTopicType.Website);
    }

    @Test
    public void canExtractEntitiesForWebsite() {
        final Document document = getDocument("oganalyzer/website");

        final List<WebTopic> topics = entityExtractor.analyze(document, language);

        assertThat(topics.size()).isEqualTo(1);
    }

    @Test
    public void canExtractGoodValues() {
        final Document document = getDocument("oganalyzer/website");

        final List<WebTopic> topics = entityExtractor.analyze(document, language);

        final WebTopic webTopic = topics.get(0);
        assertThat(webTopic.getType()).isEqualTo(WebTopicType.Website);
        assertThat(webTopic.getName(language)).isEqualTo(WordUtils.capitalizeFully("Le Monde.fr - Actualité à la Une"));
        assertThat(webTopic.getDescription(language)).isEqualTo("Le Monde.fr - 1er site d'information. Les articles du journal et toute l'actualité en continu : International, France, Société, Economie, Culture, Environnement, Blogs ...");

    }

    private Document getDocument(final String value) {
        final Scraper scraper = new Scraper();
        final String uri = internet.uri(value);
        return scraper.scrap(uri);
    }
}
