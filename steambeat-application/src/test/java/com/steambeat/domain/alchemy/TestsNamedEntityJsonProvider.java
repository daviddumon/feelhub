package com.steambeat.domain.alchemy;

import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.KeywordFactory;
import com.steambeat.domain.reference.*;
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
        alchemyNamedEntityJsonProvider = new NamedEntityJsonProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder(new KeywordService(new KeywordFactory(), new ReferenceFactory())));
    }

    @Test
    public void canGetNamedEntitiesForAWebPage() {
        final Reference reference = TestFactories.references().newReference();

        //final List<NamedEntity> results = alchemyNamedEntityJsonProvider.entitiesFor(reference);
        //
        //assertThat(results, notNullValue());
        //assertThat(results.size(), is(19));
    }

    private NamedEntityJsonProvider alchemyNamedEntityJsonProvider;
}
