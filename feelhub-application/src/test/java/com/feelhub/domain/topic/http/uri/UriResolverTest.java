package com.feelhub.domain.topic.http.uri;

import com.feelhub.test.FakeInternet;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.restlet.data.MediaType;

import static org.fest.assertions.Assertions.*;

public class UriResolverTest {

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
        final Uri uri = new Uri(internet.uri("http://liberation.fr"));

        final ResolverResult resolverResult = uriResolver.resolve(uri);

        assertThat(resolverResult.getPath().size()).isEqualTo(2);
        assertThat(resolverResult.getPath().get(0)).isEqualTo(uri);
        assertThat(resolverResult.getPath().get(1)).isEqualTo(new Uri(internet.uri("http://www.liberation.fr")));
    }

    @Test
    public void canResolveCanonicalDirectly() {
        final Uri uri = new Uri(internet.uri("http://www.gameblog.fr"));

        final ResolverResult resolverResult = uriResolver.resolve(uri);

        assertThat(resolverResult.getPath().get(0)).isEqualTo(uri);
    }

    @Test
    public void throwsExceptionOn404() {
        expectedException.expect(UriException.class);
        uriResolver.resolve(new Uri(internet.uri("http://www.feelhub.com/404")));
    }

    @Test
    public void throwsExceptionOnBadHost() {
        expectedException.expect(UriException.class);
        uriResolver.resolve(new Uri(internet.uri("http://www.badurlunknowhost.com")));
    }

    @Test
    public void returnOriginalAdressOn5xxError() {
        final Uri uri = new Uri(internet.uri("http://www.stackoverflow.com"));

        final ResolverResult resolverResult = uriResolver.resolve(uri);

        assertThat(resolverResult.getPath().get(0)).isEqualTo(uri);
    }

    @Test
    public void returnMediaType() {
        final Uri uri = new Uri(internet.uri("http://www.liberation.fr"));

        final ResolverResult resolverResult = uriResolver.resolve(uri);

        assertThat(resolverResult.getMediaType()).isEqualTo(MediaType.TEXT_HTML);
    }

    private UriResolver uriResolver;
}
