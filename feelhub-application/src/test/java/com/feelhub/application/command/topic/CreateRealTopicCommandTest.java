package com.feelhub.application.command.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class CreateRealTopicCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canCreateARealTopic() {
        final User fakeActiveUser = TestFactories.users().createFakeActiveUser("mail@mail.com");

        UUID topicId = new CreateRealTopicCommand(FeelhubLanguage.REFERENCE, "name", RealTopicType.Automobile, fakeActiveUser.getId()).execute();


        Topic realTopic = Repositories.topics().get(topicId);
        assertThat(realTopic).isNotNull();
        assertThat(realTopic.getUserId()).isEqualTo(fakeActiveUser.getId());
        assertThat(realTopic.getName(FeelhubLanguage.REFERENCE)).isEqualTo("Name");
        assertThat(Repositories.topics().getAll().size()).isEqualTo(1);
    }

}
