package com.feelhub.domain.feeling;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public class FeelingFactoryTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        feelingFactory = new FeelingFactory();
    }

    @Test
    public void canCreateAFeeling() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final String text = "hi!";

        final Feeling feeling = feelingFactory.createFeeling(text, user.getId());

        assertThat(feeling.getId()).isNotNull();
        assertThat(feeling.getText()).isEqualTo(text);
        assertThat(feeling.getUserId()).isEqualTo(user.getId());
    }

    private FeelingFactory feelingFactory;
}
