package com.feelhub.application;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import com.google.inject.*;
import org.junit.*;

import java.util.*;

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
            }
        });
        feelingService = injector.getInstance(FeelingService.class);
    }

    @Test
    public void canAddFeelingAndSentiments() {
        final FeelingRequestEvent event = getEvent();

        feelingService.handle(event);

        assertThat(Repositories.feelings().getAll().size()).isEqualTo(1);
        final Feeling feeling = Repositories.feelings().get(event.getFeelingId());
        assertThat(feeling).isNotNull();
        assertThat(feeling.getText()).isEqualTo(event.getText());
        assertThat(feeling.getUserId()).isEqualTo(event.getUserId());
        assertThat(feeling.getLanguageCode()).isEqualTo(event.getLanguage().getCode());
        assertThat(feeling.getSentiments().size()).isEqualTo(3);
        assertThat(Repositories.related().getAll().size()).isEqualTo(6);
    }

    private FeelingRequestEvent getEvent() {
        topic = TestFactories.topics().newCompleteRealTopic();
        user = TestFactories.users().createFakeActiveUser("feeling@mail.com");
        final FeelingRequestEvent.Builder builder = new FeelingRequestEvent.Builder();
        builder.feelingId(UUID.randomUUID());
        builder.user(user);
        builder.languageCode(FeelhubLanguage.reference());
        builder.text("C'est cool :)");
        builder.sentiments(getSentiments());
        return builder.build();
    }


    public List<Sentiment> getSentiments() {
        List<Sentiment> sentiments = Lists.newArrayList();
        sentiments.add(TestFactories.sentiments().newBadSentiment());
        sentiments.add(TestFactories.sentiments().newGoodSentiment());
        sentiments.add(TestFactories.sentiments().newGoodSentiment());
        return sentiments;
    }

    private User user;
    private RealTopic topic;
    private FeelingService feelingService;
}
