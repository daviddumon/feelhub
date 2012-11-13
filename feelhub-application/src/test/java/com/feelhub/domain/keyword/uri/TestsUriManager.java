package com.feelhub.domain.keyword.uri;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.google.inject.*;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsUriManager {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final FakeUriResolver uriResolver = new FakeUriResolver();
        canonicalUri = "http://www.canonical.com";
        uriResolver.thatFind(canonicalUri);
        exceptionUri = "http://www.exception";
        uriResolver.ThatThrow(exceptionUri);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(UriResolver.class).toInstance(uriResolver);
            }
        });
        uriManager = injector.getInstance(UriManager.class);
    }

    @Test
    public void addKeywordsToCompleteUriEvent() {
        final String uri = "http://www.test.com";

        final List<String> tokens = uriManager.getTokens(uri);

        assertThat(tokens.size(), is(4));
        assertThat(tokens.get(0), is("http://www.test.com"));
        assertThat(tokens.get(1), is("www.test.com"));
        assertThat(tokens.get(2), is(canonicalUri));
        assertThat(tokens.get(3), is(canonicalUri.replaceFirst("http://", "")));
    }

    @Test
    public void throwExceptionOnBadUri() {
        exception.expect(UriException.class);

        uriManager.getTokens(exceptionUri);
    }

    private String canonicalUri;
    private String exceptionUri;
    private UriManager uriManager;
}
