package com.steambeat.domain.keyword;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.domain.translation.TranslationDoneEvent;
import com.steambeat.repositories.Repositories;
import com.steambeat.test.TestFactories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsKeywordListener {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        keywordListener = new KeywordListener(new KeywordFactory());
    }

    @Test
    public void canBuildAKeywordFromAnEvent() {
        final String value = "value";
        final Language fr = Language.forString("fr");
        final Keyword keyword = TestFactories.keywords().newKeyword(value, fr);
        final TranslationDoneEvent event = new TranslationDoneEvent(keyword, "translatedValue");

        keywordListener.handle(event);

        assertThat(Repositories.keywords().getAll().size(), is(2));
        final Keyword translatedKeyword = Repositories.keywords().forValueAndLanguage("translatedValue", Language.reference());
        assertThat(translatedKeyword, notNullValue());
        assertThat(translatedKeyword.getTopicId(), is(keyword.getTopicId()));
    }

    @Test
    public void canUseGoodTopicIfAlreadyExist() {
        final Keyword hello = TestFactories.keywords().newKeyword("hello", Language.reference());
        final Keyword coucou = TestFactories.keywords().newKeyword("coucou", Language.forString("fr"));
        final TranslationDoneEvent event = new TranslationDoneEvent(coucou, "hello");

        keywordListener.handle(event);

        final List<Keyword> keywords = Repositories.keywords().getAll();
        assertThat(keywords.size(), is(2));
        assertThat(keywords.get(0).getTopicId(), is(hello.getTopicId()));
        assertThat(keywords.get(1).getTopicId(), is(hello.getTopicId()));
    }

    @Test
    public void listenToTranslationDoneEvent() {
        final Keyword coucou = TestFactories.keywords().newKeyword("coucou", Language.forString("fr"));
        final TranslationDoneEvent event = new TranslationDoneEvent(coucou, "hello");

        DomainEventBus.INSTANCE.post(event);

        final List<Keyword> keywords = Repositories.keywords().getAll();
        assertThat(keywords.size(), is(2));
    }

    private KeywordListener keywordListener;
}
