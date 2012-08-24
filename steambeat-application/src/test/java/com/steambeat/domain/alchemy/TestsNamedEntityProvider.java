package com.steambeat.domain.alchemy;

import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import org.junit.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsNamedEntityProvider {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        alchemyNamedEntityProvider = new NamedEntityProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder());
    }

    @Test
    public void canGetNamedEntitiesForAWebPage() {
        final String uri = "http://www.mypage.com";

        final List<NamedEntity> results = alchemyNamedEntityProvider.entitiesFor(uri);

        assertThat(results, notNullValue());
        assertThat(results.size(), is(19));
    }

    private NamedEntityProvider alchemyNamedEntityProvider;
}
