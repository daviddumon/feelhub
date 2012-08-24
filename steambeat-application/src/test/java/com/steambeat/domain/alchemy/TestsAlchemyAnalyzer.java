package com.steambeat.domain.alchemy;

import com.steambeat.application.*;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.reference.*;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.mockito.Mockito.*;

public class TestsAlchemyAnalyzer {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void setUp() throws Exception {
        entityProvider = mock(NamedEntityProvider.class);
        analyzer = new AlchemyAnalyzer(entityProvider, new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory())));
    }

    @Test
    public void ifNoKeywordsCreateNothing() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWithoutKeywords());
        final Reference reference = TestFactories.references().newReference();
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

    //    final List<Subject> uris = Repositories.uris().getAll();
    //    assertThat(uris.size(), is(1));
    //    final List<Association> associations = Repositories.associations().getAll();
    //    assertThat(associations.size(), is(1));
    //    final List<Relation> relations = Repositories.relations().getAll();
    //    assertThat(relations.size(), is(0));
    }

    //@Test
    //public void doNotCreateConceptFromExistingConcept() {
    //    final Uri uri = TestFactories.uris().newUri();
    //    final List<NamedEntity> namedEntities = TestFactories.alchemy().namedEntityWith1Keyword();
    //    when(entityProvider.entitiesFor(uri)).thenReturn(namedEntities);
    //
    //    analyzer.analyze(uri);
    //
    //    final List<Subject> uris = Repositories.uris().getAll();
    //    assertThat(uris.size(), is(1));
    //}
    //
    //@Test
    //public void createConceptIfNoPreviousConcept() {
    //    final Uri uri = TestFactories.uris().newUri();
    //    when(entityProvider.entitiesFor(uri)).thenReturn(TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept());
    //
    //    analyzer.analyze(uri);
    //
    //    final List<Subject> uris = Repositories.uris().getAll();
    //    assertThat(uris.size(), is(2));
    //}
    //
    //@Test
    //public void createAssociationForKeyword() {
    //    final Uri uri = TestFactories.uris().newUri();
    //    when(entityProvider.entitiesFor(uri)).thenReturn(TestFactories.alchemy().namedEntityWith1Keyword());
    //
    //    analyzer.analyze(uri);
    //
    //    final List<Association> associations = Repositories.associations().getAll();
    //    assertThat(associations.size(), is(2));
    //}
    //
    //@Test
    //public void createAssociationFor2Keywords() {
    //    final Uri uri = TestFactories.uris().newUri();
    //    when(entityProvider.entitiesFor(uri)).thenReturn(TestFactories.alchemy().namedEntityWith2Keywords());
    //
    //    analyzer.analyze(uri);
    //
    //    final List<Association> associations = Repositories.associations().getAll();
    //    assertThat(associations.size(), is(3));
    //}
    //
    //@Test
    //public void createAssociationFromKeywordOnlyIfNotExisting() {
    //    final Uri uri = TestFactories.uris().newUri();
    //    final List<NamedEntity> namedEntities = TestFactories.alchemy().namedEntityWith2Keywords();
    //    final NamedEntity namedEntity = namedEntities.get(0);
    //    TestFactories.associations().newAssociation(new Tag(namedEntity.keywords.get(0)), namedEntity.conceptId, namedEntity.language);
    //    when(entityProvider.entitiesFor(uri)).thenReturn(namedEntities);
    //
    //    analyzer.analyze(uri);
    //
    //    final List<Association> associations = Repositories.associations().getAll();
    //    assertThat(associations.size(), is(3));
    //}
    //
    //@Test
    //public void dontCreateConceptsIfAlreadyExisting() {
    //    final Uri uri = TestFactories.uris().newUri();
    //
    //    when(entityProvider.entitiesFor(uri)).thenReturn(TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept());
    //    analyzer.analyze(uri);
    //    when(entityProvider.entitiesFor(uri)).thenReturn(TestFactories.alchemy().namedEntityWith1Keyword());
    //    analyzer.analyze(uri);
    //
    //    final List<Subject> uris = Repositories.uris().getAll();
    //    assertThat(uris.size(), is(2));
    //}
    //
    //@Test
    //public void createRelationsBetweenConceptsAndPages() {
    //    final Uri uri = TestFactories.uris().newUri();
    //    when(entityProvider.entitiesFor(uri)).thenReturn(TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept());
    //
    //    analyzer.analyze(uri);
    //
    //    final List<Relation> relations = Repositories.relations().getAll();
    //    assertThat(relations.size(), is(2));
    //    final Subject concept = Repositories.uris().getAll().get(1);
    //    testRelation(uri, concept, relations.get(0));
    //    testRelation(concept, uri, relations.get(1));
    //}
    //
    //@Test
    //public void canCreateRelationsBetweenAllConceptsAndWebpages() {
    //    final Uri uri = TestFactories.uris().newUri();
    //    when(entityProvider.entitiesFor(uri)).thenReturn(TestFactories.alchemy().namedEntitiesWithoutConcepts(5));
    //
    //    analyzer.analyze(uri);
    //
    //    final List<Relation> relations = Repositories.relations().getAll();
    //    assertThat(relations.size(), is(30));
    //}
    //
    //@Test
    //public void initialRelationWeightIsRelevanceOfEntityPlusOne() {
    //    final Uri uri = TestFactories.uris().newUri();
    //    final List<NamedEntity> namedEntities = TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept();
    //    when(entityProvider.entitiesFor(uri)).thenReturn(namedEntities);
    //
    //    analyzer.analyze(uri);
    //
    //    final List<Relation> relations = Repositories.relations().getAll();
    //    assertThat(relations.get(0).getWeight(), is(namedEntities.get(0).relevance + 1));
    //    assertThat(relations.get(1).getWeight(), is(namedEntities.get(0).relevance + 1));
    //}
    //
    //@Test
    //public void addWeightToExistingConcepts() {
    //    final Uri uri = TestFactories.uris().newUri();
    //    when(entityProvider.entitiesFor(uri)).thenReturn(TestFactories.alchemy().namedEntityWith1KeywordWithoutConcept());
    //
    //    analyzer.analyze(uri);
    //    analyzer.analyze(uri);
    //
    //    final List<Relation> relations = Repositories.relations().getAll();
    //    assertThat(relations.size(), is(2));
    //    assertThat(relations.get(0).getWeight(), is(3.0));
    //}
    //
    //private void testRelation(final Uri left, final Uri right, final Relation relation) {
    //    assertThat(relation.getLeft(), is(left));
    //    assertThat(relation.getRight(), is(right));
    //}
    //
    private NamedEntityProvider entityProvider;
    private AlchemyAnalyzer analyzer;
}
