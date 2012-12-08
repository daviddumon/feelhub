package com.feelhub.domain.alchemy;

import com.feelhub.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.usable.real.RealTopicType;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsNamedEntityFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        namedEntityFactory = injector.getInstance(NamedEntityFactory.class);
    }

    @Test
    public void canFindTagsFromGoodEntity() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNotNull();
        assertThat(namedEntity.tags).isNotNull();
        assertThat(namedEntity.tags.size()).isEqualTo(2);
        assertThat(namedEntity.tags.get(0)).isEqualTo(alchemyJsonEntity.text);
        assertThat(namedEntity.tags.get(1)).isEqualTo(alchemyJsonEntity.disambiguated.name);
    }

    @Test
    public void canFindTagsFromGoodEntityWithouDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntityWithoutDisambiguated();

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNotNull();
        assertThat(namedEntity.tags).isNotNull();
        assertThat(namedEntity.tags.size()).isEqualTo(1);
        assertThat(namedEntity.tags.get(0)).isEqualTo(alchemyJsonEntity.text);
    }

    @Test
    public void addOnlyOneTagIfTextAndNameAreTheSame() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntityWithoutDisambiguated();
        alchemyJsonEntity.disambiguated.name = alchemyJsonEntity.text;

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNotNull();
        assertThat(namedEntity.tags).isNotNull();
        assertThat(namedEntity.tags.size()).isEqualTo(1);
        assertThat(namedEntity.tags.get(0)).isEqualTo(alchemyJsonEntity.text);
    }

    @Test
    public void checkForSizeOfText() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.text = "a";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNotNull();
        assertThat(namedEntity.tags).isNotNull();
        assertThat(namedEntity.tags.size()).isEqualTo(1);
    }

    @Test
    public void checkForSizeOfName() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.disambiguated.name = "a";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNotNull();
        assertThat(namedEntity.tags).isNotNull();
        assertThat(namedEntity.tags.size()).isEqualTo(1);
    }

    @Test
    public void checkForSpecialCharacterInText() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.text = "text-";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNotNull();
        assertThat(namedEntity.tags).isNotNull();
        assertThat(namedEntity.tags.size()).isEqualTo(1);
    }

    @Test
    public void checkForSpecialCharacterInName() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.disambiguated.name = "name{";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNotNull();
        assertThat(namedEntity.tags).isNotNull();
        assertThat(namedEntity.tags.size()).isEqualTo(1);
    }

    @Test
    public void canReturnGoodValuesForEntityWithoutDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntityWithoutDisambiguated();

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity.typeReal).isEqualTo(RealTopicType.Other);
        assertThat(namedEntity.feelhubLanguage).isEqualTo(FeelhubLanguage.none());
        assertThat(namedEntity.relevance).isEqualTo(alchemyJsonEntity.relevance);
    }

    @Test
    public void canReturnGoodValuesForEntityWithDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity.typeReal).isEqualTo(RealTopicType.Other);
        assertThat(namedEntity.feelhubLanguage).isEqualTo(FeelhubLanguage.none());
        assertThat(namedEntity.relevance).isEqualTo(alchemyJsonEntity.relevance);
        assertThat(namedEntity.subType).isEqualTo(alchemyJsonEntity.disambiguated.subType);
        assertThat(namedEntity.website).isEqualTo(alchemyJsonEntity.disambiguated.website);
        assertThat(namedEntity.geo).isEqualTo(alchemyJsonEntity.disambiguated.geo);
        assertThat(namedEntity.dbpedia).isEqualTo(alchemyJsonEntity.disambiguated.dbpedia);
        assertThat(namedEntity.yago).isEqualTo(alchemyJsonEntity.disambiguated.yago);
        assertThat(namedEntity.opencyc).isEqualTo(alchemyJsonEntity.disambiguated.opencyc);
        assertThat(namedEntity.umbel).isEqualTo(alchemyJsonEntity.disambiguated.umbel);
        assertThat(namedEntity.freebase).isEqualTo(alchemyJsonEntity.disambiguated.freebase);
        assertThat(namedEntity.ciaFactbook).isEqualTo(alchemyJsonEntity.disambiguated.ciaFactbook);
        assertThat(namedEntity.census).isEqualTo(alchemyJsonEntity.disambiguated.census);
        assertThat(namedEntity.geonames).isEqualTo(alchemyJsonEntity.disambiguated.geonames);
        assertThat(namedEntity.musicBrainz).isEqualTo(alchemyJsonEntity.disambiguated.musicBrainz);
        assertThat(namedEntity.crunchbase).isEqualTo(alchemyJsonEntity.disambiguated.crunchbase);
        assertThat(namedEntity.semanticCrunchbase).isEqualTo(alchemyJsonEntity.disambiguated.semanticCrunchbase);
    }

    @Test
    public void languageDependsOnType() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.type = "Automobile";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity.feelhubLanguage).isEqualTo(FeelhubLanguage.none());
    }

    @Test
    public void unknownTopicTypeHasNoLanguage() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.type = "Fdsf";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity.feelhubLanguage).isEqualTo(FeelhubLanguage.none());
    }

    @Test
    public void returnNullIfNoTags() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.text = "";
        alchemyJsonEntity.disambiguated.name = "";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNull();
    }

    @Test
    public void trimText() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntityWithoutDisambiguated();
        alchemyJsonEntity.text = " text ";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNotNull();
        assertThat(namedEntity.tags).isNotNull();
        assertThat(namedEntity.tags.size()).isEqualTo(1);
        assertThat(namedEntity.tags.get(0)).isEqualTo("text");
    }

    @Test
    public void trimName() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntityWithoutDisambiguated();
        alchemyJsonEntity.text = "";
        alchemyJsonEntity.disambiguated.name = " text ";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity).isNotNull();
        assertThat(namedEntity.tags).isNotNull();
        assertThat(namedEntity.tags.size()).isEqualTo(1);
        assertThat(namedEntity.tags.get(0)).isEqualTo("text");
    }

    @Test
    public void ifUnknownTypeSetTopicTypeToUnknown() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.namedEntities().alchemyJsonEntity();
        alchemyJsonEntity.type = "Fdsf";

        final NamedEntity namedEntity = namedEntityFactory.build(alchemyJsonEntity);

        assertThat(namedEntity.typeReal).isEqualTo(RealTopicType.Other);
    }

    private NamedEntityFactory namedEntityFactory;
}
