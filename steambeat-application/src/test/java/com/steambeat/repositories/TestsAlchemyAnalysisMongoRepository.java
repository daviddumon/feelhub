package com.steambeat.repositories;

import com.mongodb.*;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.test.*;
import org.junit.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAlchemyAnalysisMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        entityMongoRepository = Repositories.alchemyAnalysis();
    }

    @Test
    public void canPersistAlchemyAnalysis() {
        final Keyword keyword = TestFactories.keywords().newKeyword();
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(keyword);
        alchemyAnalysis.setLanguageCode(SteambeatLanguage.reference());

        entityMongoRepository.add(alchemyAnalysis);

        final DBObject alchemyAnalysisFound = getAlchemyAnalysisFromDB(alchemyAnalysis.getId());
        assertThat(alchemyAnalysisFound, notNullValue());
        assertThat(alchemyAnalysisFound.get("referenceId"), is((Object) alchemyAnalysis.getReferenceId()));
        assertThat(alchemyAnalysisFound.get("value").toString(), is(alchemyAnalysis.getValue()));
        assertThat(alchemyAnalysisFound.get("languageCode").toString(), is(alchemyAnalysis.getLanguageCode()));
    }

    @Test
    public void canGetAlchemyAnalysis() {
        final DBCollection collection = mongo.getCollection("alchemyanalysis");
        final DBObject alchemyAnalysis = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        alchemyAnalysis.put("_id", id);
        collection.insert(alchemyAnalysis);

        final AlchemyAnalysis alchemyAnalysisFound = entityMongoRepository.get(id);

        assertThat(alchemyAnalysisFound, notNullValue());
    }

    @Test
    public void canGetForAReference() {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.alchemy().newAlchemyAnalysis(reference);
        TestFactories.alchemy().newAlchemyAnalysis();
        TestFactories.alchemy().newAlchemyAnalysis();

        final List<AlchemyAnalysis> alchemyEntities = entityMongoRepository.forReferenceId(reference.getId());

        assertThat(alchemyEntities, notNullValue());
        assertThat(alchemyEntities.size(), is(1));
    }

    private DBObject getAlchemyAnalysisFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("alchemyanalysis");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private AlchemyAnalysisRepository entityMongoRepository;
}
