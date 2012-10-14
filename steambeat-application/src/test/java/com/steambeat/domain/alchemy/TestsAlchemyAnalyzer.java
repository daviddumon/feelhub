package com.steambeat.domain.alchemy;

public class TestsAlchemyAnalyzer {
    //
    //@Rule
    //public WithFakeRepositories repositories = new WithFakeRepositories();
    //
    //@Rule
    //public WithDomainEvent bus = new WithDomainEvent();
    //
    //@Before
    //public void setUp() throws Exception {
    //    entityProvider = mock(NamedEntityProvider.class);
    //    new AlchemyAnalyzer(new FakeSessionProvider(), entityProvider, new KeywordService(new KeywordFactory(), new SubjectIdentifier()));
    //}
    //
    //@Test
    //public void ifNoKeywordsCreateNothing() {
    //    when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWithoutKeywords());
    //    final Reference reference = TestFactories.references().newReference();
    //    final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    assertThat(Repositories.alchemys().getAll().size(), is(0));
    //    assertThat(Repositories.keywords().getAll().size(), is(0));
    //    assertThat(Repositories.references().getAll().size(), is(1));
    //}
    //
    //@Test
    //public void canAnalyseTheGoodUri() {
    //    final Reference reference = TestFactories.references().newReference();
    //    final Keyword keyword = TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
    //    final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    verify(entityProvider).entitiesFor(keyword.getValue());
    //}
    //
    //@Test
    //public void postConceptGroupEvent() {
    //    bus.capture(ConceptGroupEvent.class);
    //    final Reference reference = TestFactories.references().newReference();
    //    TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
    //    final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    final ConceptGroupEvent conceptGroupEvent = bus.lastEvent(ConceptGroupEvent.class);
    //    assertThat(conceptGroupEvent, notNullValue());
    //}
    //
    //@Test
    //public void canCreateFromNamedEntity() {
    //    when(entityProvider.entitiesFor(anyString())).thenReturn(TestFactories.namedEntities().namedEntityWith1Keyword());
    //    final Reference reference = TestFactories.references().newReference();
    //    TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
    //    final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    assertThat(Repositories.keywords().getAll().size(), is(2));
    //    assertThat(Repositories.references().getAll().size(), is(2));
    //    assertThat(Repositories.alchemys().getAll().size(), is(1));
    //}
    //
    //@Test
    //public void keepTheUriReferenceId() {
    //    bus.capture(ConceptGroupEvent.class);
    //    final Reference reference = TestFactories.references().newReference();
    //    TestFactories.keywords().newKeyword("http://www.google.fr", SteambeatLanguage.none(), reference);
    //    final UriReferencesChangedEvent event = TestFactories.events().newUriReferencesChangedEvent(reference.getId());
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    final ConceptGroupEvent conceptGroupEvent = bus.lastEvent(ConceptGroupEvent.class);
    //    assertThat(conceptGroupEvent, notNullValue());
    //    assertThat(conceptGroupEvent.getReferenceId(), is(event.getNewReferenceId()));
    //}
    //
    //private NamedEntityProvider entityProvider;
}
