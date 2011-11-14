package com.steambeat.application;

import com.steambeat.repositories.Repositories;
import com.steambeat.domain.subject.feed.*;
import com.steambeat.test.fakeFactories.FakeFeedFactory;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.fakeServices.FakeAssociationService;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.*;

public class TestsFeedService {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        feedService = new FeedService(new FakeAssociationService(), new FakeFeedFactory());
    }

    @Test
    public void canGetFeedFromRepo() {
        final Feed feed = TestFactories.feeds().newFeed("uri");

        final Feed feedFound = feedService.lookUpFeed(new Uri("uri"));

        assertThat(feedFound, is(feed));
    }

    @Test
    public void canAddToRepository() {
        final Feed feedFound = feedService.addFeed(new Uri("uri"));

        assertThat(Repositories.feeds().getAll().size(), is(1));
        assertThat(Repositories.feeds().getAll(), hasItem(feedFound));
    }

    @Test
    public void throwsExceptionOnFailLookup() {
        exception.expect(FeedNotYetCreatedException.class);
        feedService.lookUpFeed(badUri());
    }

    private Uri badUri() {
        return new Uri("a fail uri");
    }

    private FeedService feedService;
}
