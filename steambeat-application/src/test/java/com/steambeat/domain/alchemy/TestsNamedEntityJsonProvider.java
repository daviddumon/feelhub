package com.steambeat.domain.alchemy;

import com.steambeat.application.AssociationService;
import com.steambeat.domain.topic.Topic;
import com.steambeat.test.*;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestsNamedEntityJsonProvider {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        alchemyNamedEntityJsonProvider = new NamedEntityJsonProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder(new AssociationService(new FakeUriPathResolver(), new FakeMicrosoftTranslator())));
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
