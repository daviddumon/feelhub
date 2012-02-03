package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.DomainEventBus;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsWebPageFactory {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public WithFakeRepositories fakeRepositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Rule
    public FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        webPageFactory = new WebPageFactory();
    }

    @Test
    public void canBuildWebPage() {
        final Association association = associate("http://lemonde.fr/international", "http://www.lemonde.fr/international");

        final WebPage webPage = webPageFactory.newWebPage(association);

        assertThat(webPage, notNullValue());
        assertThat(webPage.getId(), is("http://www.lemonde.fr/international"));
    }

    @Test
    public void cannotCreateAWebPageTwice() {
        final Association association = associate("http://lemonde.fr/international", "http://lemonde.fr/international");
        TestFactories.webPages().newWebPage("http://lemonde.fr/international");

        expectedException.expect(WebPageAlreadyExistsException.class);
        expectedException.expect(hasProperty("uri", is("http://lemonde.fr/international")));
        webPageFactory.newWebPage(association);
    }

    @Test
    public void canSpreadEvent() {
        bus.capture(WebPageCreatedEvent.class);
        final Association association = associate("http://lemonde.fr/international", "http://www.lemonde.fr/international");
        DomainEventBus.INSTANCE.notifyOnSpread();

        final WebPage webPage = webPageFactory.newWebPage(association);

        final WebPageCreatedEvent lastEvent = bus.lastEvent(WebPageCreatedEvent.class);
        assertThat(lastEvent, notNullValue());
        assertThat(lastEvent.getWebPage(), is(webPage));
        assertThat(lastEvent.getDate(), is(time.getNow()));
    }

    private Association associate(final String uri, final String canonicalUri) {
        final Association association = TestFactories.associations().newAssociation(uri, canonicalUri);
        return association;
    }

    private WebPageFactory webPageFactory;
}
