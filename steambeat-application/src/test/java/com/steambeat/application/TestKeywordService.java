package com.steambeat.application;

import com.steambeat.domain.eventbus.WithDomainEvent;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestKeywordService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        keywordService = new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory()));
    }

    @Test
    public void canLookUpAKeyword() {
        final Keyword keyword = TestFactories.keywords().newKeyword();

        final Keyword foundKeyword = keywordService.lookUp(keyword.getValue(), keyword.getLanguage());

        assertThat(foundKeyword, notNullValue());
    }

    @Test
    public void throwExceptionOnKeywordNotFound() {
        exception.expect(KeywordNotFound.class);

        keywordService.lookUp("any", SteambeatLanguage.forString("any"));
    }

    @Test
    public void canCreateAKeyword() {
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("none");

        final Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        assertThat(keyword, notNullValue());
        assertThat(keyword.getValue(), is(value));
        assertThat(keyword.getLanguage(), is(steambeatLanguage));
        assertThat(Repositories.keywords().getAll().size(), is(1));
    }

    @Test
    public void createAndPersistAReferenceFirst() {
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("none");

        keywordService.createKeyword(value, steambeatLanguage);

        final List<Reference> references = Repositories.references().getAll();
        final List<Keyword> keywords = Repositories.keywords().getAll();
        assertThat(keywords.size(), is(1));
        assertThat(references.size(), is(1));
    }

    @Test
    public void postEventOnKeywordCreation() {
        bus.capture(KeywordCreatedEvent.class);
        final String value = "value";
        final SteambeatLanguage steambeatLanguage = SteambeatLanguage.forString("english");

        Keyword keyword = keywordService.createKeyword(value, steambeatLanguage);

        final KeywordCreatedEvent keywordCreatedEvent = bus.lastEvent(KeywordCreatedEvent.class);
        assertThat(keywordCreatedEvent, notNullValue());
        assertThat(keywordCreatedEvent.getKeyword(), is(keyword));
        assertThat(keywordCreatedEvent.getDate(), is(time.getNow()));
    }


    //
    //@Test
    //public void canCreateAllAssociationsInRepository() {
    //    final UriPathResolver pathResolver = new FakeUriPathResolver().thatFind(new Uri("http://www.liberation.fr"));
    //    final AssociationService associationService = new AssociationService(pathResolver, new FakeMicrosoftTranslator());
    //    final Uri uri = new Uri("http://liberation.fr");
    //
    //    final Association association = associationService.createAssociationsFor(uri);
    //
    //    assertThat(association, notNullValue());
    //    final List<Association> associations = Repositories.associations().getAll();
    //    assertThat(associations.size(), is(2));
    //}
    //
    //@Test
    //public void associationIdentifierIsCanonicalUri() {
    //    final String canonicalAddress = "http://www.liberation.fr";
    //    final UriPathResolver pathResolver = new FakeUriPathResolver().thatFind(new Uri(canonicalAddress));
    //    final AssociationService associationService = new AssociationService(pathResolver, new FakeMicrosoftTranslator());
    //    final Uri uri = new Uri("http://liberation.fr");
    //
    //    final Association association = associationService.createAssociationsFor(uri);
    //
    //    assertThat(association.getIdentifier(), is(canonicalAddress));
    //}
    //
    //@Test
    //public void allAssociationsFromAnUriGetSameSubjectId() {
    //    final String canonicalAddress = "http://www.liberation.fr";
    //    final UriPathResolver pathResolver = new FakeUriPathResolver().thatFind(new Uri(canonicalAddress));
    //    final AssociationService associationService = new AssociationService(pathResolver, new FakeMicrosoftTranslator());
    //    final Uri uri = new Uri("http://liberation.fr");
    //
    //    associationService.createAssociationsFor(uri);
    //
    //    final List<Association> associations = Repositories.associations().getAll();
    //    assertThat(associations.get(0).getSubjectId(), is(associations.get(1).getSubjectId()));
    //}
    //
    //@Test
    //public void canUseEncodedResources() throws UnsupportedEncodingException {
    //    final Uri uri = new Uri(URLEncoder.encode("http://www.lemonde.fr", "UTF-8"));
    //
    //    final Association association = associationService.createAssociationsFor(uri);
    //
    //    assertThat(association.getIdentifier(), is(uri.toString()));
    //}
    //
    //@Test
    //public void canGetAnAssociationForAnIdentifierAndLanguage() {
    //    final Language language = Language.forString("english");
    //    final Tag identifier = new Tag("test");
    //    TestFactories.associations().newAssociation(identifier, UUID.randomUUID(), language);
    //
    //    final Association association = associationService.lookUp(identifier, language);
    //
    //    assertThat(association, notNullValue());
    //    assertThat(association.getIdentifier(), is(identifier.toString()));
    //    assertThat(association.getLanguage(), is(language.getCode()));
    //}
    //
    //@Test
    //public void alwaysCreateTheEnglishTag() {
    //    final Tag tag = new Tag("tag");
    //    final UUID uuid = UUID.randomUUID();
    //
    //    associationService.createAssociationFor(tag, uuid, Language.forString("french"));
    //
    //    final List<Association> associations = Repositories.associations().getAll();
    //    assertThat(associations.size(), is(2));
    //    assertThat(associations.get(1).getSubjectId(), is(uuid));
    //    assertThat(associations.get(1).getLanguage(), is("french"));
    //    assertThat(associations.get(0).getSubjectId(), is(uuid));
    //    assertThat(associations.get(0).getLanguage(), is("english"));
    //}
    //
    //private AssociationService associationService;
    private KeywordService keywordService;
}
