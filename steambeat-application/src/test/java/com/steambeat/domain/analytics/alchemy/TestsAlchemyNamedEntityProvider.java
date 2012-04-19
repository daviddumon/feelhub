package com.steambeat.domain.analytics.alchemy;

import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.test.FakeInternet;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import org.junit.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAlchemyNamedEntityProvider {


    @Rule
    public FakeInternet internet = new FakeInternet();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        Document document = getFakeDocumentFromFile();
        alchemyNamedEntityProvider = new AlchemyNamedEntityProvider(new FakeAlchemyAPI(document));
    }

    @Test
    public void canGetApiKey() {
        assertThat(alchemyNamedEntityProvider.getApiKey(), is("testapikey"));
    }

    @Test
    public void canGetNamedEntitiesForAWebPage() {
        final WebPage webPage = TestFactories.subjects().newWebPage();

        final List<NamedEntity> namedEntities = alchemyNamedEntityProvider.entitiesFor(webPage);

        assertThat(namedEntities, notNullValue());
        assertThat(namedEntities.size(), is(37));
    }

    private Document getFakeDocumentFromFile() throws ParserConfigurationException, IOException, SAXException {
        final File file = new File("steambeat-application/src/test/java/com/steambeat/domain/analytics/alchemy/alchemy.xml");
        final DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        final Document document = documentBuilder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    private AlchemyNamedEntityProvider alchemyNamedEntityProvider;
}
