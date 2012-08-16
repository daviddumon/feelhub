package com.steambeat.domain.reference;

import com.steambeat.test.SystemTime;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsReference {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateAReference() {
        final UUID id = UUID.randomUUID();

        final Reference reference = new Reference(id);

        assertThat(reference.getId(), notNullValue());
        assertThat(reference.getId(), is(id));
        assertThat(reference.getCreationDate(), is(time.getNow()));
        assertThat(reference.getCreationDate(), is(reference.getLastModificationDate()));
        assertThat(reference.isActive(), is(true));
    }

    @Test
    public void canSetLastModificationDate() {
        final UUID id = UUID.randomUUID();
        final Reference reference = new Reference(id);
        time.waitHours(1);

        reference.setLastModificationDate(new DateTime());

        assertThat(reference.getLastModificationDate(), is(time.getNow()));
    }

    //@Test
    //    public void cannotCreateAWebPageTwice() {
    //        final WebPage webPage = TestFactories.subjects().newWebPage();
    //
    //        expectedException.expect(WebPageAlreadyExistsException.class);
    //        webPageFactory.newWebPage(new Association(new Uri("http://lemonde.fr/international"), webPage.getId()));
    //    }
    //
    //    @Test
    //    public void canSpreadEvent() {
    //        bus.capture(WebPageCreatedEvent.class);
    //        final Association association = TestFactories.associations().newAssociation(new Uri("http://www.steambeat.com"));
    //
    //        final WebPage webPage = webPageFactory.newWebPage(association);
    //
    //        final WebPageCreatedEvent lastEvent = bus.lastEvent(WebPageCreatedEvent.class);
    //        assertThat(lastEvent, notNullValue());
    //        assertThat(lastEvent.getWebPage(), is(webPage));
    //        assertThat(lastEvent.getDate(), is(time.getNow()));
    //    }
}
