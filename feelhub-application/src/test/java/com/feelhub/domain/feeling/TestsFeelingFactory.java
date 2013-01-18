package com.feelhub.domain.feeling;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsFeelingFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        feelingFactory = new FeelingFactory();
    }

    @Test
    public void canCreateAFeeling() {
        final UUID id = UUID.randomUUID();
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final String text = "hi!";

        final Feeling feeling = feelingFactory.createFeeling(id, text, user.getId());

        assertThat(feeling.getId()).isEqualTo(id);
        assertThat(feeling.getRawText()).isEqualTo(text);
        assertThat(feeling.getUserId()).isEqualTo(user.getId());
    }

    private FeelingFactory feelingFactory;
}
