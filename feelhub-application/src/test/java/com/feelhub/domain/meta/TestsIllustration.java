package com.feelhub.domain.meta;

import com.feelhub.test.SystemTime;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsIllustration {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateAnIllustration() {
        final String link = "http//www.illustrationuri.com";
        final UUID topicId = UUID.randomUUID();

        final Illustration illustration = new Illustration(topicId, link);

        assertThat(illustration.getId(), notNullValue());
        assertThat(illustration.getTopicId(), is(topicId));
        assertThat(illustration.getLink(), is(link));
        assertThat(illustration.getCreationDate(), is(time.getNow()));
        assertThat(illustration.getLastModificationDate(), is(time.getNow()));
    }
}
