package com.steambeat.domain.analytics.alchemy;

import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyXmlEntity;
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

public class TestsAlchemyNamedEntityProvider {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        alchemyNamedEntityProvider = new AlchemyNamedEntityProvider(new FakeAlchemyLink());
    }

    @Test
    public void canGetNamedEntitiesForAWebPage() {
        final WebPage webPage = TestFactories.subjects().newWebPage();

        final List<AlchemyXmlEntity> results = alchemyNamedEntityProvider.entitiesFor(webPage);

        assertThat(results, notNullValue());
        assertThat(results.size(), is(37));
        final AlchemyXmlEntity alchemyXmlEntity = results.get(0);
        assertThat(alchemyXmlEntity.language, is("english"));
    }

    private AlchemyNamedEntityProvider alchemyNamedEntityProvider;
}
