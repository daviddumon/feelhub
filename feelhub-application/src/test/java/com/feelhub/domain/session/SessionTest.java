package com.feelhub.domain.session;

import com.feelhub.domain.user.User;
import com.feelhub.test.SystemTime;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class SessionTest {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void aSessionHasAnExpirationDate() {
        final Session session = new Session(new DateTime().plusHours(1), new User());

        assertThat(session.getExpirationDate(), notNullValue());
        assertThat(session.getExpirationDate(), is(time.getNow().plusHours(1)));
    }

    @Test
    public void canCheckExpiration() {
        final Session session = new Session(new DateTime().plusHours(1), new User());

        time.waitHours(2);

        assertTrue(session.isExpired());
    }

    @Test
    public void sessionTokenIsTheID() {
        final Session session = new Session(new DateTime().plusHours(1), new User());

        assertThat((UUID) session.getId(), is(session.getToken()));
    }
}
