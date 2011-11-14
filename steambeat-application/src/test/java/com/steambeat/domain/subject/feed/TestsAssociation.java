package com.steambeat.domain.subject.feed;

import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.test.*;
import org.joda.time.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAssociation {

    @Rule
    public SystemTime systemTime = SystemTime.fixed();

    @Test
    public void creationDateIsSet() {
        final Association association = new Association(new Uri("toto"), TestFactories.canonicalUriFinder());

        final DateTime expirationDate = association.getExpirationDate();

        assertThat(expirationDate, is(new DateTime().plusDays(7)));
    }

    @Test
    public void canCheckIsAlive() {
        final Association association = new Association(new Uri("plop"), TestFactories.canonicalUriFinder());
        DateTimeUtils.setCurrentMillisFixed(new DateTime().plusDays(8).getMillis());

        assertThat(association.isAlive(), is(false));
    }

    @Test
    public void canFollowRedirectionOnCreation() {
        final Uri canonicalUri = new Uri("http://www.liberation.fr");
        final CanonicalUriFinder finder = TestFactories.canonicalUriFinder().thatFind(canonicalUri);

        final Association association = new Association(new Uri("http://liberation.fr"), finder);

        assertThat(association.getCanonicalUri(), is(canonicalUri.toString()));
    }

    @Test
    public void updatingChangesTheExpirationDateAndCanonical() {
        final StubCanonicalUriFinder finder = TestFactories.canonicalUriFinder();
        final Association association = new Association(new Uri("http://www.gameblog.fr"), finder);
        systemTime.waitDays(8);

        association.update(finder.thatFind(new Uri("http://plop")));

        assertThat(association.isAlive(), is(true));
        assertThat(association.getCanonicalUri(), is("http://plop"));
    }
}
