package com.steambeat.domain.alchemy;

import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.topic.Topic;
import com.steambeat.test.TestFactories;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import org.junit.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestsNamedEntityJsonProvider {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        alchemyNamedEntityJsonProvider = new NamedEntityJsonProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder(new KeywordService(new KeywordFactory())));
    }

    @Test
    public void canGetNamedEntitiesForAWebPage() {
        final Topic topic = TestFactories.topics().newTopic();

        //final List<NamedEntity> results = alchemyNamedEntityJsonProvider.entitiesFor(topic);
        //
        //assertThat(results, notNullValue());
        //assertThat(results.size(), is(19));
    }

    private NamedEntityJsonProvider alchemyNamedEntityJsonProvider;
}
