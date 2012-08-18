package com.steambeat.domain.illustration;

import com.steambeat.test.SystemTime;
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
        final UUID referenceId = UUID.randomUUID();

        final Illustration illustration = new Illustration(referenceId, link);

        assertThat(illustration.getId(), notNullValue());
        assertThat(illustration.getReferenceId(), is(referenceId));
        assertThat(illustration.getLink(), is(link));
        assertThat(illustration.getCreationDate(), is(time.getNow()));
        assertThat(illustration.getLastModificationDate(), is(time.getNow()));
    }
}
