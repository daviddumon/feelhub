package com.steambeat.domain.subject.webpage;

import com.steambeat.domain.analytics.Association;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsWebPage {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canCreateAWebPage() {
        final Uri uri = new Uri("http://www.steambeat.com");
        final Association association = TestFactories.associations().newAssociation(uri);

        final WebPage webPage = TestFactories.subjects().newWebPageFor(association);

        assertThat(webPage.getId(), notNullValue());
        assertThat(webPage.getId().toString(), not(uri.toString()));
        assertThat(webPage.getRealUri(), is(uri));
    }

    @Test
    public void hasASemanticDescription() {
        final Uri uri = new Uri("http://www.steambeat.com");
        final Association association = TestFactories.associations().newAssociation(uri);

        final WebPage webPage = TestFactories.subjects().newWebPageFor(association);

        assertThat(webPage.getSemanticDescription(), is("www-steambeat-com-token1-token2-token3"));
    }
}
