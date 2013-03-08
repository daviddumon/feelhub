package com.feelhub.application.command.feeling;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class CreateFeelingCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Test
    public void canAddFeelingAndSentiments() {
        final CreateFeelingCommand command = createCommand();

        UUID feelingId = command.execute();

        assertThat(Repositories.feelings().getAll().size()).isEqualTo(1);
        final Feeling feeling = Repositories.feelings().get(feelingId);
        assertThat(feeling).isNotNull();
        assertThat(feeling.getText()).isEqualTo(command.text);
        assertThat(feeling.getUserId()).isEqualTo(command.userId);
        assertThat(feeling.getLanguageCode()).isEqualTo(command.language.getCode());
        assertThat(feeling.getSentiments().size()).isEqualTo(3);
        assertThat(Repositories.related().getAll().size()).isEqualTo(6);
    }

    private CreateFeelingCommand createCommand() {
        final User user = TestFactories.users().createFakeActiveUser("feeling@mail.com");
        final CreateFeelingCommand.Builder builder = new CreateFeelingCommand.Builder();
        builder.feelingId(UUID.randomUUID());
        builder.user(user);
        builder.languageCode(FeelhubLanguage.reference());
        builder.text("C'est cool :)");
        builder.sentiments(getSentiments());
        return builder.build();
    }


    public List<Sentiment> getSentiments() {
        final List<Sentiment> sentiments = Lists.newArrayList();
        sentiments.add(TestFactories.sentiments().newBadSentiment());
        sentiments.add(TestFactories.sentiments().newGoodSentiment());
        sentiments.add(TestFactories.sentiments().newGoodSentiment());
        return sentiments;
    }

}
