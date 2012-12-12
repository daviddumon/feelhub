package com.feelhub.repositories;

import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.test.*;
import com.mongodb.*;
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
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final AlchemyAnalysis alchemyAnalysis = new AlchemyAnalysis(realTopic, "value");
        alchemyAnalysis.setLanguageCode(FeelhubLanguage.reference());

        entityMongoRepository.add(alchemyAnalysis);

        final DBObject alchemyAnalysisFound = getAlchemyAnalysisFromDB(alchemyAnalysis.getId());
        assertThat(alchemyAnalysisFound, notNullValue());
        assertThat(alchemyAnalysisFound.get("topicId"), is((Object) alchemyAnalysis.getTopicId()));
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
    public void canGetForATopic() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        TestFactories.alchemy().newAlchemyAnalysis(realTopic);
        TestFactories.alchemy().newAlchemyAnalysis();
        TestFactories.alchemy().newAlchemyAnalysis();

        final List<AlchemyAnalysis> alchemyEntities = entityMongoRepository.forTopicId(realTopic.getId());

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
