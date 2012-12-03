package com.feelhub.application;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.tag.uri.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.translation.*;
import com.feelhub.repositories.*;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import com.google.inject.*;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsFeelingService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(SessionProvider.class).to(FakeSessionProvider.class);
                bind(Translator.class).to(FakeTranslator.class);
                bind(UriResolver.class).to(FakeUriResolver.class);
            }
        });
        injector.getInstance(FeelingService.class);
    }

    @Ignore
    @Test
    public void canAddFeelingAndSentiments() {
        TestFactories.tags().newWord("word3", FeelhubLanguage.fromCode("fr"));
        final FeelingRequestEvent event = getEvent();

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.feelings().getAll().size()).isEqualTo(1);
        final Feeling feeling = Repositories.feelings().get(event.getFeelingId());
        assertThat(feeling).isNotNull();
        assertThat(feeling.getText()).isEqualTo(event.getText());
        assertThat(feeling.getUserId()).isEqualTo(event.getUserId());
        assertThat(feeling.getLanguageCode()).isEqualTo(event.getLanguage().getCode());
        assertThat(feeling.getSentiments().size()).isEqualTo(3);
        assertThat(Repositories.relations().getAll().size()).isEqualTo(6);
    }

    @Ignore
    @Test
    public void createSemanticContextWithGoodValueAndLanguage() {
        final Tag word3 = TestFactories.tags().newWord("word3", FeelhubLanguage.fromCountryName("french"));
        final Tag word4 = TestFactories.tags().newWord("word4", FeelhubLanguage.fromCountryName("french"));
        TestFactories.relations().newRelation(word3.getTopicId(), word4.getTopicId());
        final FeelingRequestEvent event = getEvent();

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.feelings().getAll().size()).isEqualTo(1);
        final Feeling feeling = Repositories.feelings().get(event.getFeelingId());
        assertThat(feeling.getSentiments().size()).isEqualTo(4);
    }

    private FeelingRequestEvent getEvent() {
        final FeelingRequestEvent.Builder builder = new FeelingRequestEvent.Builder();
        builder.feelingId(UUID.randomUUID());
        builder.user(TestFactories.users().createFakeActiveUser("feeling@mail.com"));
        //builder.sentimentValue(SentimentValue.good);
        //builder.keywordValue("word3");
        builder.languageCode(FeelhubLanguage.reference());
        //builder.userLanguageCode(FeelhubLanguage.fromCode("fr"));
        builder.text("+word1 -word2 word4");
        return builder.build();
    }
}
