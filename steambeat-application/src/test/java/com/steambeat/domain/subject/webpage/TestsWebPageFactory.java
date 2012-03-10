package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.repositories.TestWithMongoRepository;
import com.steambeat.test.*;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsWebPageFactory extends TestWithMongoRepository {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        webPageFactory = new WebPageFactory();
    }

    @Test
    public void canBuildWebPage() {
        final Uri uri = new Uri("http://lemonde.fr/international");
        final Association association = TestFactories.associations().newAssociation(uri);

        final WebPage webPage = webPageFactory.newWebPage(association);

        assertThat(webPage, notNullValue());
        assertThat(webPage.getId(), is(association.getSubjectId().toString()));
    }

    @Test
    public void cannotCreateAWebPageTwice() {
        final WebPage webPage = TestFactories.subjects().newWebPage();

        expectedException.expect(WebPageAlreadyExistsException.class);
        webPageFactory.newWebPage(new Association(new Uri("http://lemonde.fr/international"), UUID.fromString(webPage.getId())));
    }

    @Test
    public void canSpreadEvent() {
        bus.capture(WebPageCreatedEvent.class);
        final Association association = TestFactories.associations().newAssociation(new Uri("http://www.steambeat.com"));
        DomainEventBus.INSTANCE.notifyOnSpread();

        final WebPage webPage = webPageFactory.newWebPage(association);

        final WebPageCreatedEvent lastEvent = bus.lastEvent(WebPageCreatedEvent.class);
        assertThat(lastEvent, notNullValue());
        assertThat(lastEvent.getWebPage(), is(webPage));
        assertThat(lastEvent.getDate(), is(time.getNow()));
    }

    private WebPageFactory webPageFactory;
}
