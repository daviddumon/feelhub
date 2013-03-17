package com.feelhub.application.command.session;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CreateSessionCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void canCreateASessionForAnUser() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final UUID sessionId = new CreateSessionCommand(user.getId(), new DateTime().plusHours(1)).execute();

        final Session session = Repositories.sessions().get(sessionId);
        assertThat(session, notNullValue());
        assertThat(session.getUserId(), is(user.getId()));
        assertThat(session.getToken(), notNullValue());
    }

}
