package com.steambeat.domain.topic;

import com.steambeat.test.SystemTime;
import org.joda.time.DateTime;
import org.junit.*;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestsTopic {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canCreateATopic() {
        final UUID id = UUID.randomUUID();

        final Topic topic = new Topic(id);

        assertThat(topic.getId(), notNullValue());
        assertThat(topic.getId(), is(id));
        assertThat(topic.getCreationDate(), is(time.getNow()));
        assertThat(topic.getCreationDate(), is(topic.getLastModificationDate()));
        assertThat(topic.isActive(), is(true));
    }

    @Test
    public void canSetLastModificationDate() {
        final UUID id = UUID.randomUUID();
        final Topic topic = new Topic(id);
        time.waitHours(1);

        topic.setLastModificationDate(new DateTime());

        assertThat(topic.getLastModificationDate(), is(time.getNow()));
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
