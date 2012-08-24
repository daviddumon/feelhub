package com.steambeat.domain.alchemy;

import com.steambeat.domain.reference.Reference;
import com.steambeat.repositories.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.TestFactories;
import org.junit.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestsNamedEntityProvider {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        alchemyNamedEntityProvider = new NamedEntityProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder());
    }

    @Test
    public void canGetNamedEntitiesForAWebPage() {
        final Reference reference = TestFactories.references().newReference();

        //final List<NamedEntity> results = alchemyNamedEntityJsonProvider.entitiesFor(reference);
        //
        //assertThat(results, notNullValue());
        //assertThat(results.size(), is(19));
    }

    private NamedEntityProvider alchemyNamedEntityProvider;
}
