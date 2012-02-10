package com.steambeat.tools;

import com.steambeat.domain.subject.webpage.*;
import com.steambeat.test.FakeInternet;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsCanonicalUriFinder {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Rule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        finder = new CanonicalUriFinder();
    }

    @AfterClass
    public static void afterClass() {
        internet.stop();
    }

    @Test
    public void throwsExceptionOn404() {
        expectedException.expect(WebPageException.class);
        finder.find(internet.uri("http://www.steambeat.com/404"));
    }

    @Test
    public void throwsExceptionOnBadHost() {
        expectedException.expect(WebPageException.class);
        finder.find(internet.uri("http://www.badurlunknowhost.com"));
    }

    @Test
    public void canResolveCanonicalDirectly() {
        final Uri uri = internet.uri("http://www.gameblog.fr");

        final Uri uriFound = finder.find(uri);

        assertThat(uriFound, is(uri));
    }

    @Test
    public void canFollowRedirection() {
        final Uri uri = internet.uri("http://liberation.fr");

        final Uri canonicalFound = finder.find(uri);

        assertThat(canonicalFound, is(internet.uri("http://www.liberation.fr")));
    }

    @Test
    public void returnOriginalAdressOn5xxError() {
        final Uri uri = internet.uri("http://www.stackoverflow.com");

        final Uri canonical = finder.find(uri);

        assertThat(canonical, is(uri));
    }

    private CanonicalUriFinder finder;
}
