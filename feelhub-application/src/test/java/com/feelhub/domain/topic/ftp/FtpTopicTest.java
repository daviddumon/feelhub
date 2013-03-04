package com.feelhub.domain.topic.ftp;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class FtpTopicTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canCreateAnFtpTopic() {
        final UUID id = UUID.randomUUID();

        final FtpTopic topic = new FtpTopic(id);

        assertThat(topic.getId()).isEqualTo(id);
        assertThat(topic.getCurrentId()).isEqualTo(id);
        assertThat(topic.getType()).isEqualTo(FtpTopicType.Ftp);
    }
}
