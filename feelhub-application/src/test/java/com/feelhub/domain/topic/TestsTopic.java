package com.feelhub.domain.topic;

import com.feelhub.test.SystemTime;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsTopic {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateATopic() {
        final UUID id = UUID.randomUUID();

        final Topic topic = new Topic(id);

        assertThat(topic.getId(), notNullValue());
        assertThat(topic.getId(), is(id));
        assertThat(topic.getCreationDate(), is(time.getNow()));
        assertThat(topic.getCreationDate(), is(topic.getLastModificationDate()));
        assertThat(topic.isActive(), is(true));
    }

    @Test
    public void canSetLastModificationDate() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        time.waitHours(1);

        topic.setLastModificationDate(new DateTime());

        assertThat(topic.getLastModificationDate(), is(time.getNow()));
    }
}
