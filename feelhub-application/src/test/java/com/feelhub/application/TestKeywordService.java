package com.feelhub.application;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.domain.translation.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

public class TestKeywordService {

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(Translator.class).to(FakeTranslator.class);
                bind(UriResolver.class).to(FakeUriResolver.class);
            }
        });
        keywordService = injector.getInstance(KeywordService.class);
    }


    private KeywordService keywordService;
}
