package com.steambeat.domain.uri;

import com.steambeat.test.FakeInternet;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUriPathResolver {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        pathResolver = new UriPathResolver();
    }

    @Test
    public void canFollowRedirection() {
        final Uri uri = new Uri(internet.uri("http://liberation.fr"));

        final List<Uri> path = pathResolver.resolve(uri);

        assertThat(path.size(), is(2));
        assertThat(path.get(0), is(new Uri(internet.uri("http://liberation.fr"))));
        assertThat(path.get(1), is(new Uri(internet.uri("http://www.liberation.fr"))));
    }

    @Test
    public void canResolveCanonicalDirectly() {
        final Uri uri = new Uri(internet.uri("http://www.gameblog.fr"));

        final List<Uri> path = pathResolver.resolve(uri);

        assertThat(path.get(0), is(uri));
    }

    @Test
    public void throwsExceptionOn404() {
        expectedException.expect(UriPathResolverException.class);
        pathResolver.resolve(new Uri(internet.uri("http://www.steambeat.com/404")));
    }

    @Test
    public void throwsExceptionOnBadHost() {
        expectedException.expect(UriPathResolverException.class);
        pathResolver.resolve(new Uri(internet.uri("http://www.badurlunknowhost.com")));
    }

    @Test
    public void returnOriginalAdressOn5xxError() {
        final Uri uri = new Uri(internet.uri("http://www.stackoverflow.com"));

        final List<Uri> path = pathResolver.resolve(uri);

        assertThat(path.get(0), is(uri));
    }

    private UriPathResolver pathResolver;
}
