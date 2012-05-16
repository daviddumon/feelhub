package com.steambeat.domain.alchemy;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsNamedEntityJsonProvider {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        alchemyNamedEntityJsonProvider = new ANamedEntityJsonProvider(new FakeJsonAlchemyLink());
    }

    @Test
    public void canGetNamedEntitiesForAWebPage() {
        final WebPage webPage = TestFactories.subjects().newWebPage();

        final List<NamedEntity> results = alchemyNamedEntityJsonProvider.entitiesFor(webPage);

        assertThat(results, notNullValue());
        assertThat(results.size(), is(19));
    }

    private ANamedEntityJsonProvider alchemyNamedEntityJsonProvider;
}
