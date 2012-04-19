package com.steambeat.domain.analytics.alchemy;

import com.steambeat.domain.analytics.identifiers.uri.Uri;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.FakeInternet;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestsAlchemyNamedEntityProvider {

    @Rule
    public FakeInternet internet = new FakeInternet();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        alchemyNamedEntityProvider = new AlchemyNamedEntityProvider();
        final Uri uri = internet.uri("alchemy");
        alchemyNamedEntityProvider.setRequestUri(uri.toString());
    }

    @Test
    public void canGetApiKey() {
        assertThat(alchemyNamedEntityProvider.getApiKey(), is("testapikey"));
    }

    @Test
    @Ignore
    public void canGetNamedEntitiesForAWebPage() {
        final WebPage webPage = TestFactories.subjects().newWebPage();

        final List<NamedEntity> namedEntities = alchemyNamedEntityProvider.entitiesFor(webPage);

        assertThat(namedEntities, notNullValue());
        assertThat(namedEntities.size(), is(3));
    }

    private AlchemyNamedEntityProvider alchemyNamedEntityProvider;
}
