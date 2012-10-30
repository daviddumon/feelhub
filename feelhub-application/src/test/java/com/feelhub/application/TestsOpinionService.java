package com.feelhub.application;

import com.feelhub.domain.eventbus.*;
import com.feelhub.domain.opinion.*;
import com.feelhub.domain.relation.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.*;
import com.feelhub.test.TestFactories;
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
        new OpinionService(new FakeSessionProvider(), new FakeKeywordService(), new OpinionRelationBinder(new RelationBuilder(new RelationFactory())));
    }

    @Test
    public void canAddOpinionAndJudgements() {
        TestFactories.keywords().newKeyword("keyword2", FeelhubLanguage.forString("french"));
        final OpinionRequestEvent event = getEvent();

        DomainEventBus.INSTANCE.post(event);

        assertThat(Repositories.opinions().getAll().size(), is(1));
        final Opinion opinion = Repositories.opinions().get(event.getOpinionId());
        assertThat(opinion, notNullValue());
        assertThat(opinion.getText(), is(event.getText()));
        assertThat(opinion.getUserId(), is(event.getUserId()));
        assertThat(opinion.getLanguageCode(), is(event.getUserLanguageCode()));
        assertThat(opinion.getJudgments().size(), is(3));
        assertThat(Repositories.relations().getAll().size(), is(6));
    }

    private OpinionRequestEvent getEvent() {
        final OpinionRequestEvent.Builder builder = new OpinionRequestEvent.Builder();
        builder.opinionId(UUID.randomUUID());
        builder.user(TestFactories.users().createFakeActiveUser("opinion@mail.com"));
        builder.feeling(Feeling.good);
        builder.keywordValue("keyword3");
        builder.languageCode(FeelhubLanguage.reference().getCode());
        builder.userLanguageCode(FeelhubLanguage.forString("french").getCode());
        builder.text("+keyword1 -keyword2");
        return builder.build();
    }
}
