package com.steambeat.application;

import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

public class TestKeywordService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canLookUpAKeyword() {

    }

    //@Rule
    //public WithFakeRepositories repositories = new WithFakeRepositories();
    //
    //@Rule
    //public ExpectedException exception = ExpectedException.none();
    //
    //@Before
    //public void before() {
    //    this.associationService = new AssociationService(new FakeUriPathResolver(), new FakeMicrosoftTranslator());
    //}
    //
    //@Test
    //public void canGetAnAssociation() {
    //    final Uri uri = new Uri("http://www.steambeat.com");
    //    final Association association = TestFactories.associations().newAssociation(uri);
    //
    //    final Association foundAssociation = associationService.lookUp(uri);
    //
    //    assertThat(foundAssociation, notNullValue());
    //    assertThat(foundAssociation.getIdentifier(), is(uri.toString()));
    //    assertThat(foundAssociation, is(association));
    //}
    //
    //@Test
    //public void throwErrorIfNoAssociationForUri() {
    //    exception.expect(AssociationNotFound.class);
    //    final Uri uri = new Uri("http://www.steambeat.com");
    //
    //    associationService.lookUp(uri);
    //}
    //
    //@Test
    //public void canCreateANewAssociation() {
    //    final Uri uri = new Uri("http://www.steambeat.com");
    //
    //    final Association association = associationService.createAssociationsFor(uri);
    //
    //    assertThat(association, notNullValue());
    //    assertThat(association.getIdentifier(), is(uri.toString()));
    //    assertThat(association.getLanguage(), is(""));
    //}
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
}
