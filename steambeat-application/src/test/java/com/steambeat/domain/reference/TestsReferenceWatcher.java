package com.steambeat.domain.reference;

import com.steambeat.domain.keyword.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

public class TestsReferenceWatcher {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        referenceWatcher = new ReferenceWatcher(new KeywordFactory());
    }
    //
    //@Test
    //public void canBuildAKeywordFromAnEvent() {
    //    final String value = "value";
    //    final Language fr = Language.forString("fr");
    //    final Keyword keyword = TestFactories.keywords().newKeyword(value, fr);
    //    //final TranslationDoneEvent event = new TranslationDoneEvent(keyword, "translatedValue");
    //
    //    referenceWatcher.handle(event);
    //
    //    assertThat(Repositories.keywords().getAll().size(), is(2));
    //    final Keyword translatedKeyword = Repositories.keywords().forValueAndLanguage("translatedValue", Language.reference());
    //    assertThat(translatedKeyword, notNullValue());
    //    assertThat(translatedKeyword.getReferenceId(), is(keyword.getReferenceId()));
    //}
    //
    //@Test
    //public void canUseGoodReferenceIfAlreadyExist() {
    //    final Keyword hello = TestFactories.keywords().newKeyword("hello", Language.reference());
    //    final Keyword coucou = TestFactories.keywords().newKeyword("coucou", Language.forString("fr"));
    //    //final TranslationDoneEvent event = new TranslationDoneEvent(coucou, "hello");
    //
    //    referenceWatcher.handle(event);
    //
    //    final List<Keyword> keywords = Repositories.keywords().getAll();
    //    assertThat(keywords.size(), is(2));
    //    assertThat(keywords.get(0).getReferenceId(), is(hello.getReferenceId()));
    //    assertThat(keywords.get(1).getReferenceId(), is(hello.getReferenceId()));
    //}
    //
    //@Test
    //public void listenToTranslationDoneEvent() {
    //    final Keyword coucou = TestFactories.keywords().newKeyword("coucou", Language.forString("fr"));
    //    final TranslationDoneEvent event = new TranslationDoneEvent(coucou, "hello");
    //
    //    DomainEventBus.INSTANCE.post(event);
    //
    //    final List<Keyword> keywords = Repositories.keywords().getAll();
    //    assertThat(keywords.size(), is(2));
    //}

    private ReferenceWatcher referenceWatcher;
}
