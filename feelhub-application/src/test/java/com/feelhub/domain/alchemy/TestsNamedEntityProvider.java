package com.feelhub.domain.alchemy;

import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsNamedEntityProvider {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {
        alchemyNamedEntityProvider = new NamedEntityProvider(new FakeJsonAlchemyLink(), new NamedEntityBuilder());
    }

    @Test
    public void canGetNamedEntitiesForAnUri() {
        final String uri = "http://www.mypage.com";
        final Keyword keyword = TestFactories.keywords().newWord(uri);

        final List<NamedEntity> results = alchemyNamedEntityProvider.entitiesFor(keyword);

        assertThat(results, notNullValue());
        assertThat(results.size(), is(19));
    }

    @Test
    public void createAlchemyAnalysisForUri() {
        final String uri = "http://www.mypage.com";
        final Keyword keyword = TestFactories.keywords().newWord(uri);

        alchemyNamedEntityProvider.entitiesFor(keyword);

        final List<AlchemyAnalysis> alchemyAnalysisList = Repositories.alchemyAnalysis().getAll();
        assertThat(alchemyAnalysisList.size(), is(1));
        assertThat(alchemyAnalysisList.get(0).getLanguageCode(), is(FeelhubLanguage.forString("english").getCode()));
    }

    @Test
    public void throwExceptionOnError() {
        exception.expect(AlchemyException.class);
        final String uri = "http://www.error.com";
        final Keyword keyword = TestFactories.keywords().newWord(uri);

        alchemyNamedEntityProvider.entitiesFor(keyword);
    }

    private NamedEntityProvider alchemyNamedEntityProvider;
}
