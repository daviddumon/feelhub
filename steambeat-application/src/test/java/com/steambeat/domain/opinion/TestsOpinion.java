package com.steambeat.domain.opinion;

import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.feed.Feed;
import com.steambeat.test.SystemTime;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsOpinion {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Test
    public void canCreateAnOpinion() {
        final String value = "opinion";
        final Feeling feeling = Feeling.good;
        final Feed feed = TestFactories.feeds().newFeed();

        final Opinion opinion = new Opinion(value, feeling, feed);

        assertThat(opinion, notNullValue());
        assertThat(opinion.getText(), is(value));
        assertThat(opinion.getFeeling(), is(feeling));
        assertThat(opinion.getCreationDate(), notNullValue());
        assertThat(opinion.getCreationDate(), is(time.getNow()));
        assertThat(opinion.getSubject(), is((Subject) feed));
    }

}
