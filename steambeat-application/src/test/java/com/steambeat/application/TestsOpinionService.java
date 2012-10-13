package com.steambeat.application;

import com.google.common.collect.Lists;
import com.steambeat.domain.eventbus.*;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.repositories.fakeRepositories.*;
import com.steambeat.test.TestFactories;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinionService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        new OpinionService(new FakeSessionProvider());
    }

    @Test
    public void canAddOpinionAndJudgements() {
        final OpinionRequestEvent event = getEvent();

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.opinions().getAll().size(), is(1));
        assertThat(Repositories.opinions().getAll().get(0).getId().toString(), is(event.getOpinionId()));
        assertThat(Repositories.opinions().getAll().get(0).getText(), is(event.getText()));
    }

    private OpinionRequestEvent getEvent() {
        final OpinionRequestEvent.Builder builder = new OpinionRequestEvent.Builder();
        builder.opinionId(UUID.randomUUID().toString());
        builder.user(TestFactories.users().createFakeActiveUser("opinion@mail.com"));
        builder.feeling(Feeling.good);
        builder.keywordValue("keyword");
        builder.languageCode(SteambeatLanguage.reference().getCode());
        builder.userLanguageCode(SteambeatLanguage.forString("french").getCode());
        builder.text("opinion");
        return builder.build();
    }


}
