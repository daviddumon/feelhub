package com.feelhub.application.command.session;

import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class DeleteSessionCommandTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        user = TestFactories.users().createFakeActiveUser("mail@mail.com");
    }


    @Test
    public void canDeteleSession() {
        final Session session = TestFactories.sessions().createSessionFor(user);

        new DeleteSessionCommand(session.getToken()).execute();

        assertThat(Repositories.sessions().getAll().size(), is(0));
    }


    private User user;
}
