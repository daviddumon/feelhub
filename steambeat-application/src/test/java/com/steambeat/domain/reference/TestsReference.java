package com.steambeat.domain.reference;

import com.steambeat.test.SystemTime;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsReference {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateAReference() {
        final UUID id = UUID.randomUUID();

        final Reference reference = new Reference(id);

        assertThat(reference.getId(), notNullValue());
        assertThat(reference.getId(), is(id));
        assertThat(reference.getCreationDate(), is(time.getNow()));
        assertThat(reference.getCreationDate(), is(reference.getLastModificationDate()));
        assertThat(reference.isActive(), is(true));
    }

    @Test
    public void canSetLastModificationDate() {
        final UUID id = UUID.randomUUID();
        final Reference reference = new Reference(id);
        time.waitHours(1);

        reference.setLastModificationDate(new DateTime());

        assertThat(reference.getLastModificationDate(), is(time.getNow()));
    }
}
