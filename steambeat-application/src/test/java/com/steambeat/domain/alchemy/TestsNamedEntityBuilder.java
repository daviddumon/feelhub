package com.steambeat.domain.alchemy;

import com.steambeat.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsNamedEntityBuilder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        namedEntityBuilder = new NamedEntityBuilder();
    }

    @Test
    public void canFindKeywordsFromGoodEntity() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(2));
        assertThat(namedEntity.keywords.get(0), is(alchemyJsonEntity.text));
        assertThat(namedEntity.keywords.get(1), is(alchemyJsonEntity.disambiguated.name));
    }

    @Test
    public void canFindKeywordsFromGoodEntityWithouDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntityWithoutDisambiguated();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
        assertThat(namedEntity.keywords.get(0), is(alchemyJsonEntity.text));
    }

    @Test
    public void addOnlyOneKeywordIfTextAndNameAreTheSame() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntityWithoutDisambiguated();
        alchemyJsonEntity.disambiguated.name = alchemyJsonEntity.text;

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
        assertThat(namedEntity.keywords.get(0), is(alchemyJsonEntity.text));
    }

    @Test
    public void checkForSizeOfText() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.text = "la";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
    }

    @Test
    public void checkForSizeOfName() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.disambiguated.name = "la";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
    }

    @Test
    public void checkForSpecialCharacterInText() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.text = "text-";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
    }

    @Test
    public void checkForSpecialCharacterInName() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.disambiguated.name = "name{";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
    }

    @Test
    public void canReturnGoodValuesForEntityWithoutDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntityWithoutDisambiguated();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity.type, is(alchemyJsonEntity.type));
        assertThat(namedEntity.steambeatLanguage, is(SteambeatLanguage.forString(alchemyJsonEntity.language)));
        assertThat(namedEntity.relevance, is(alchemyJsonEntity.relevance));
    }

    @Test
    public void canReturnGoodValuesForEntityWithDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity.type, is(alchemyJsonEntity.type));
        assertThat(namedEntity.steambeatLanguage, is(SteambeatLanguage.forString(alchemyJsonEntity.language)));
        assertThat(namedEntity.relevance, is(alchemyJsonEntity.relevance));
        assertThat(namedEntity.subType, is(alchemyJsonEntity.disambiguated.subType));
        assertThat(namedEntity.website, is(alchemyJsonEntity.disambiguated.website));
        assertThat(namedEntity.geo, is(alchemyJsonEntity.disambiguated.geo));
        assertThat(namedEntity.dbpedia, is(alchemyJsonEntity.disambiguated.dbpedia));
        assertThat(namedEntity.yago, is(alchemyJsonEntity.disambiguated.yago));
        assertThat(namedEntity.opencyc, is(alchemyJsonEntity.disambiguated.opencyc));
        assertThat(namedEntity.umbel, is(alchemyJsonEntity.disambiguated.umbel));
        assertThat(namedEntity.freebase, is(alchemyJsonEntity.disambiguated.freebase));
        assertThat(namedEntity.ciaFactbook, is(alchemyJsonEntity.disambiguated.ciaFactbook));
        assertThat(namedEntity.census, is(alchemyJsonEntity.disambiguated.census));
        assertThat(namedEntity.geonames, is(alchemyJsonEntity.disambiguated.geonames));
        assertThat(namedEntity.musicBrainz, is(alchemyJsonEntity.disambiguated.musicBrainz));
        assertThat(namedEntity.crunchbase, is(alchemyJsonEntity.disambiguated.crunchbase));
        assertThat(namedEntity.semanticCrunchbase, is(alchemyJsonEntity.disambiguated.semanticCrunchbase));
    }

    @Test
    public void languageDependsOnType() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.type = "Automobile";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity.steambeatLanguage, is(SteambeatLanguage.none()));
    }

    private NamedEntityBuilder namedEntityBuilder;
}
