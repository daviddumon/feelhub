package com.steambeat.tools;

import com.steambeat.domain.subject.webpage.CanonicalUriFinder;
import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.domain.subject.webpage.WebPageException;
import com.steambeat.test.FakeInternet;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestsCanonicalUriFinder {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        fakeInternet = new FakeInternet();
    }

    @AfterClass
    public static void afterClass() {
        fakeInternet.stop();
    }

    @Before
    public void before() {
        finder = new CanonicalUriFinder();
    }

    @Test
    public void throwsExceptionOn404() {
        expectedException.expect(WebPageException.class);
        finder.find(fakeInternet.uri("http://www.steambeat.com/404"));
    }

    @Test
    public void throwsExceptionOnBadHost() {
        expectedException.expect(WebPageException.class);
        finder.find(fakeInternet.uri("http://www.badurlunknowhost.com"));
    }

    @Test
    public void canResolveCanonicalDirectly() {
        final Uri uri = fakeInternet.uri("http://www.gameblog.fr");

        final Uri uriFound = finder.find(uri);

        assertThat(uriFound, is(uri));
    }

    @Test
    public void canFollowRedirection() {
        final Uri uri = fakeInternet.uri("http://liberation.fr");

        final Uri canonicalFound = finder.find(uri);

        assertThat(canonicalFound, is(fakeInternet.uri("http://www.liberation.fr")));
    }

    @Test
    public void returnOriginalAdressOn5xxError() {
        final Uri uri = fakeInternet.uri("http://www.stackoverflow.com");

        final Uri canonical = finder.find(uri);

        assertThat(canonical, is(uri));
    }

    private CanonicalUriFinder finder;
    private static FakeInternet fakeInternet;
}
