package com.steambeat.domain.alchemy;

import com.steambeat.application.*;
import com.steambeat.domain.concept.ConceptGroupEvent;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TestsAlchemyAnalyzer {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void setUp() throws Exception {
        entityProvider = mock(NamedEntityProvider.class);
        analyzer = new AlchemyAnalyzer(new FakeSessionProvider(), entityProvider, new KeywordService(new KeywordFactory(), new ReferenceService(new ReferenceFactory())));
    }

    @Test
    public void ifNoKeywordsCreateNothing() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWithoutKeywords());
        final Reference reference = TestFactories.references().newReference();
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.alchemys().getAll().size(), is(0));
        assertThat(Repositories.keywords().getAll().size(), is(0));
        assertThat(Repositories.references().getAll().size(), is(1));
    }

    @Test
    public void canAnalyseTheGoodUri() {
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

        verify(entityProvider).entitiesFor(keyword.getValue());
    }

    @Test
    public void postConceptGroupEvent() {
        bus.capture(ConceptGroupEvent.class);
        final Reference reference = TestFactories.references().newReference();
        final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

        final ConceptGroupEvent conceptGroupEvent = bus.lastEvent(ConceptGroupEvent.class);
        assertThat(conceptGroupEvent, notNullValue());
    }

    @Test
    public void canCreateFromNamedEntity() {
        when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
        final Reference reference = TestFactories.references().newReference();
        TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
        final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.keywords().getAll().size(), is(2));
        assertThat(Repositories.references().getAll().size(), is(2));
        assertThat(Repositories.alchemys().getAll().size(), is(1));
    }

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
