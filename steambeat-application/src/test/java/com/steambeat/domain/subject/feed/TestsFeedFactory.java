package com.steambeat.domain.subject.feed;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.tools.HtmlParser;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TestsFeedFactory {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        parser = mock(HtmlParser.class);
        feedFactory = new FeedFactory(parser);
    }

    @Test
    public void canBuildFeed() {
        final Association association = associate("http://lemonde.fr/international", "http://www.lemonde.fr/international");

        final Feed feed = feedFactory.buildFeed(association);

        assertThat(feed, notNullValue());
        assertThat(feed.getUri(), is("http://www.lemonde.fr/international"));
        assertThat(feed.getId(), is("http://www.lemonde.fr/international"));
        //assertThat(feed.getTitle(), is("International - LeMonde.fr"));
    }

    @Test
    public void cannotCreateAFeedTwice() {
        final Association association = associate("http://lemonde.fr/international", "http://lemonde.fr/international");
        TestFactories.feeds().newFeed("http://lemonde.fr/international");

        expectedException.expect(FeedAlreadyExistsException.class);
        expectedException.expect(hasProperty("uri", is("http://lemonde.fr/international")));
        feedFactory.buildFeed(association);
    }

    @Test
    public void canSpreadEvent() {
        bus.capture(FeedCreatedEvent.class);
        final Association association = associate("http://lemonde.fr/international", "http://www.lemonde.fr/international");
        DomainEventBus.INSTANCE.notifyOnSpread();

        final Feed feed = feedFactory.buildFeed(association);

        FeedCreatedEvent lastEvent = bus.lastEvent(FeedCreatedEvent.class);
        assertThat(lastEvent, notNullValue());
        assertThat(lastEvent.getFeed(), is(feed));
        assertThat(lastEvent.getDate(), is(time.getNow()));
    }

    private Association associate(final String uri, final String canonicalUri) {
        final Association association = TestFactories.associations().newAssociation(uri, canonicalUri);
        when(parser.getSingleNode("title")).thenReturn("International - LeMonde.fr");
        return association;
    }

    private FeedFactory feedFactory;
    private HtmlParser parser;
}
