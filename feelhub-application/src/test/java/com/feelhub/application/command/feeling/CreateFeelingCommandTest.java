package com.feelhub.application.command.feeling;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class CreateFeelingCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Test
    public void canAddFeeling() {
        final CreateFeelingCommand command = createCommand();

        final UUID feelingId = command.execute();

        assertThat(Repositories.feelings().getAll().size()).isEqualTo(1);
        final Feeling feeling = Repositories.feelings().get(feelingId);
        assertThat(feeling).isNotNull();
        assertThat(feeling.getId()).isEqualTo(feelingId);
        assertThat(feeling.getText()).isEqualTo(command.text);
        assertThat(feeling.getUserId()).isEqualTo(command.userId);
        assertThat(feeling.getTopicId()).isEqualTo(command.topicId);
        assertThat(feeling.getFeelingValue()).isEqualTo(command.feelingValue);
        assertThat(feeling.getLanguageCode()).isEqualTo(command.language.getCode());
    }

    @Test
    public void canComputeForce() {
        final CreateFeelingCommand command = createCommand();

        command.execute();
        final UUID feelingId = command.execute();

        final Feeling feeling = Repositories.feelings().get(feelingId);
        assertThat(feeling).isNotNull();
        assertThat(feeling.getForce()).isEqualTo(2);
    }

    private CreateFeelingCommand createCommand() {
        final User user = TestFactories.users().createFakeActiveUser("feeling@mail.com");
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final CreateFeelingCommand.Builder builder = new CreateFeelingCommand.Builder();
        builder.user(user);
        builder.topic(realTopic);
        builder.languageCode(FeelhubLanguage.reference());
        builder.text("C'est cool :)");
        builder.feelingValue(FeelingValue.good);
        return builder.build();
    }
}
