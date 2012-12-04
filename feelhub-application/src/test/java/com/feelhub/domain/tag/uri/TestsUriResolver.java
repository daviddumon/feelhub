package com.feelhub.domain.tag.uri;

import com.feelhub.test.FakeInternet;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUriResolver {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @ClassRule
    public static FakeInternet internet = new FakeInternet();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        uriResolver = injector.getInstance(UriResolver.class);
    }

    @Test
    public void canFollowRedirection() {
        final String uri = internet.uri("http://liberation.fr");

        final List<String> path = uriResolver.resolve(uri);

        assertThat(path.size(), is(2));
        assertThat(path.get(0), is(uri));
        assertThat(path.get(1), is(internet.uri("http://www.liberation.fr")));
    }

    @Test
    public void canResolveCanonicalDirectly() {
        final String uri = internet.uri("http://www.gameblog.fr");

        final List<String> path = uriResolver.resolve(uri);

        assertThat(path.get(0), is(uri));
    }

    @Test
    public void throwsExceptionOn404() {
        expectedException.expect(UriException.class);
        uriResolver.resolve(internet.uri("http://www.feelhub.com/404"));
    }

    @Test
    public void throwsExceptionOnBadHost() {
        expectedException.expect(UriException.class);
        uriResolver.resolve(internet.uri("http://www.badurlunknowhost.com"));
    }

    @Test
    public void returnOriginalAdressOn5xxError() {
        final String uri = internet.uri("http://www.stackoverflow.com");

        final List<String> path = uriResolver.resolve(uri);

        assertThat(path.get(0), is(uri));
    }

    private UriResolver uriResolver;
}
