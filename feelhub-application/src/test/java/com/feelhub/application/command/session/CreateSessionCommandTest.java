package com.feelhub.application.command.session;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.feelhub.test.TestFactories;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CreateSessionCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateASessionForAnUser() {
        User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final UUID sessionId = new CreateSessionCommand(user.getId(), new DateTime().plusHours(1)).execute();

        Session session = Repositories.sessions().get(sessionId);
        assertThat(session, notNullValue());
        assertThat(session.getUserId(), is(user.getId()));
        assertThat(session.getToken(), notNullValue());
    }

}
