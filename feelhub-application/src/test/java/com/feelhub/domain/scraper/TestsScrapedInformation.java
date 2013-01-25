package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopicType;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsScrapedInformation {

    @Before
    public void before() {
        scrapedInformation = new ScrapedInformation();
    }

    @Test
    public void canAddDescriptionInformationWithScore() {
        final String description = "my description";

        scrapedInformation.addDescription(0, description);

        assertThat(scrapedInformation.getDescription()).isEqualTo(description);
    }

    @Test
    public void canGetTheBestDescription() {
        final String goodDescription = "my description";

        scrapedInformation.addDescription(0, "bad description");
        scrapedInformation.addDescription(10, goodDescription);
        scrapedInformation.addDescription(5, "another bad description");

        assertThat(scrapedInformation.getDescription()).isEqualTo(goodDescription);
    }

    @Test
    public void canGetEmptyDescription() {
        assertThat(scrapedInformation.getDescription()).isEmpty();
    }

    @Test
    public void canAddNameInformationWithScore() {
        final String name = "my name";

        scrapedInformation.addName(0, name);

        assertThat(scrapedInformation.getName()).isEqualTo(name);
    }

    @Test
    public void canGetTheBestName() {
        final String goodName = "my name";

        scrapedInformation.addName(0, "bad name");
        scrapedInformation.addName(10, goodName);
        scrapedInformation.addName(5, "another bad name");

        assertThat(scrapedInformation.getName()).isEqualTo(goodName);
    }

    @Test
    public void canGetEmptyName() {
        assertThat(scrapedInformation.getName()).isEmpty();
    }

    @Test
    public void canAddIllustrationInformationWithScore() {
        final String illustration = "my illustration";

        scrapedInformation.addIllustration(0, illustration);

        assertThat(scrapedInformation.getIllustration()).isEqualTo(illustration);
    }

    @Test
    public void canGetTheBestIllustration() {
        final String goodIllustration = "my illustration";

        scrapedInformation.addIllustration(0, "bad illustration");
        scrapedInformation.addIllustration(10, goodIllustration);
        scrapedInformation.addIllustration(5, "another bad illustration");

        assertThat(scrapedInformation.getIllustration()).isEqualTo(goodIllustration);
    }

    @Test
    public void canGetEmptyIllustration() {
        assertThat(scrapedInformation.getIllustration()).isEmpty();
    }

    @Test
    public void canAddPersonsToScrapedInformation() {
        final String person1 = "John doe";
        final String person2 = "Janne doe";

        scrapedInformation.addPerson(person1);
        scrapedInformation.addPerson(person2);

        assertThat(scrapedInformation.getPersons().size()).isEqualTo(2);
        assertThat(scrapedInformation.getPersons()).contains(person1);
        assertThat(scrapedInformation.getPersons()).contains(person2);
    }

    @Test
    public void canGetEmptyPersonsList() {
        assertThat(scrapedInformation.getPersons()).isEmpty();
    }

    @Test
    public void canAddALanguageToScrapedInformationsWithScore() {
        scrapedInformation.addLanguage(10, FeelhubLanguage.reference());
        scrapedInformation.addLanguage(20, FeelhubLanguage.fromCode("fr"));

        assertThat(scrapedInformation.getLanguage()).isEqualTo(FeelhubLanguage.fromCode("fr"));
    }

    @Test
    public void defaultLanguageIsNone() {
        assertThat(scrapedInformation.getLanguage()).isEqualTo(FeelhubLanguage.none());
    }

    @Test
    public void canParseArticleType() {
        scrapedInformation.addType("article");

        assertThat(scrapedInformation.getType()).isEqualTo(HttpTopicType.Article);
        assertThat(scrapedInformation.getOpenGraphType()).isEqualTo("article");
    }

    @Test
    public void defaultTypeIsWebsite() {
        assertThat(scrapedInformation.getType()).isEqualTo(HttpTopicType.Website);
    }

    private ScrapedInformation scrapedInformation;
}