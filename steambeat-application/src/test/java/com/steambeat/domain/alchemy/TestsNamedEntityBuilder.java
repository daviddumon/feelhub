package com.steambeat.domain.alchemy;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.association.uri.UriPathResolver;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.test.FakeMicrosoftTranslator;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsNamedEntityBuilder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        namedEntityBuilder = new NamedEntityBuilder(new AssociationService(new UriPathResolver(), new FakeMicrosoftTranslator()));
    }

    @Test
    public void canFindKeywordsFromGoodEntity() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(2));
        assertThat(namedEntity.keywords.get(0), is(alchemyJsonEntity.text));
        assertThat(namedEntity.keywords.get(1), is(alchemyJsonEntity.disambiguated.name));
    }

    @Test
    public void canFindKeywordsFromGoodEntityWithouDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntityWithoutDisambiguated();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
        assertThat(namedEntity.keywords.get(0), is(alchemyJsonEntity.text));
    }

    @Test
    public void addOnlyOneKeywordIfTextAndNameAreTheSame() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntityWithoutDisambiguated();
        alchemyJsonEntity.disambiguated.name = alchemyJsonEntity.text;

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
        assertThat(namedEntity.keywords.get(0), is(alchemyJsonEntity.text));
    }

    @Test
    public void checkForSizeOfText() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();
        alchemyJsonEntity.text = "la";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
    }

    @Test
    public void checkForSizeOfName() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();
        alchemyJsonEntity.disambiguated.name = "la";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
    }

    @Test
    public void checkForSpecialCharacterInText() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();
        alchemyJsonEntity.text = "text-";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
    }

    @Test
    public void checkForSpecialCharacterInName() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();
        alchemyJsonEntity.disambiguated.name = "name{";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity, notNullValue());
        assertThat(namedEntity.keywords, notNullValue());
        assertThat(namedEntity.keywords.size(), is(1));
    }

    @Test
    public void canReturnGoodValuesForEntityWithoutDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntityWithoutDisambiguated();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity.name, is(alchemyJsonEntity.text));
        assertThat(namedEntity.type, is(alchemyJsonEntity.type));
        assertThat(namedEntity.language, is(Language.forString(alchemyJsonEntity.language)));
        assertThat(namedEntity.relevance, is(alchemyJsonEntity.relevance));
    }

    @Test
    public void canReturnGoodValuesForEntityWithDisambiguated() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity.name, is(alchemyJsonEntity.disambiguated.name));
        assertThat(namedEntity.type, is(alchemyJsonEntity.type));
        assertThat(namedEntity.language, is(Language.forString(alchemyJsonEntity.language)));
        assertThat(namedEntity.relevance, is(alchemyJsonEntity.relevance));
        assertThat(namedEntity.subType, is(alchemyJsonEntity.disambiguated.subType));
        assertThat(namedEntity.website, is(alchemyJsonEntity.disambiguated.website));
        assertThat(namedEntity.geo, is(alchemyJsonEntity.disambiguated.geo));
    }

    @Test
    public void canFindExistingConceptForAlchemyEntityText() {
        final Tag tag = new Tag("text");
        final UUID uuid = UUID.randomUUID();
        TestFactories.associations().newAssociation(tag, uuid, Language.forString("english"));
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity.conceptId, is(uuid));
    }

    @Test
    public void useLanguageForConceptFinding() {
        final Tag tag = new Tag("text");
        final UUID uuid = UUID.randomUUID();
        TestFactories.associations().newAssociation(tag, uuid, Language.forString("english"));
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();
        alchemyJsonEntity.language = "french";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertNull(namedEntity.conceptId);
    }

    @Test
    public void canFindExistingConceptForAlchemyEntityName() {
        final Tag tag = new Tag("name");
        final UUID uuid = UUID.randomUUID();
        TestFactories.associations().newAssociation(tag, uuid, Language.forString("english"));
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity.conceptId, is(uuid));
    }

    @Test
    public void languageDependsOnType() {
        final AlchemyJsonEntity alchemyJsonEntity = TestFactories.alchemy().alchemyJsonEntity();
        alchemyJsonEntity.type = "Automobile";

        final NamedEntity namedEntity = namedEntityBuilder.build(alchemyJsonEntity);

        assertThat(namedEntity.language, is(Language.forString("")));
    }

    private NamedEntityBuilder namedEntityBuilder;
}
