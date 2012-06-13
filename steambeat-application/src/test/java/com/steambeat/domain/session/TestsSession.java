package com.steambeat.domain.session;

import com.steambeat.test.SystemTime;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsSession {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void aSessionHasAnExpirationDate() {
        final Session session = new Session();

        assertThat(session.getExpirationDate(), notNullValue());
        assertThat(session.getExpirationDate(), is(time.getNow().plusHours(1)));
    }

    @Test
    public void canCheckExpiration() {
        final Session session = new Session();

        time.waitHours(2);

        assertTrue(session.isExpired());
    }

    @Test
    public void canRenewSession() {
        final Session session = new Session();
        time.waitHours(2);

        session.renew();

        assertFalse(session.isExpired());
        assertThat(session.getExpirationDate(), is(time.getNow().plusHours(1)));
    }

    @Test
    public void renewChangeToken() {
        final Session session = new Session();
        final UUID initialToken = session.getToken();
        time.waitHours(2);

        session.renew();

        assertThat(initialToken, not(session.getToken()));
    }
}
