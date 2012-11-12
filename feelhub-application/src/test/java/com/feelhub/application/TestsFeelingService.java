package com.feelhub.application;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.relation.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeelingService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        new FeelingService(new FakeSessionProvider(), new FakeKeywordService(), new FeelingRelationBinder(new RelationBuilder(new RelationFactory())));
    }

    @Test
    public void canAddFeelingAndSentiments() {
        TestFactories.keywords().newWord("keyword2", FeelhubLanguage.forString("french"));
        final FeelingRequestEvent event = getEvent();

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.feelings().getAll().size(), is(1));
        final Feeling feeling = Repositories.feelings().get(event.getFeelingId());
        assertThat(feeling, notNullValue());
        assertThat(feeling.getText(), is(event.getText()));
        assertThat(feeling.getUserId(), is(event.getUserId()));
        assertThat(feeling.getLanguageCode(), is(event.getUserLanguageCode()));
        assertThat(feeling.getSentiments().size(), is(3));
        assertThat(Repositories.relations().getAll().size(), is(6));
    }

    private FeelingRequestEvent getEvent() {
        final FeelingRequestEvent.Builder builder = new FeelingRequestEvent.Builder();
        builder.feelingId(UUID.randomUUID());
        builder.user(TestFactories.users().createFakeActiveUser("feeling@mail.com"));
        builder.sentimentValue(SentimentValue.good);
        builder.keywordValue("keyword3");
        builder.languageCode(FeelhubLanguage.reference().getCode());
        builder.userLanguageCode(FeelhubLanguage.forString("french").getCode());
        builder.text("+keyword1 -keyword2");
        return builder.build();
    }
}
