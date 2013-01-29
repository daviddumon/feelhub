package com.feelhub.repositories;

import com.feelhub.domain.admin.*;
import com.feelhub.test.SystemTime;
import com.mongodb.*;
import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAlchemyStatistcsMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        entityMongoRepository = Repositories.alchemyStatistics();
    }

    @Test
    public void canPersistAlchemyStatistic() {
        AlchemyStatistic alchemyStatistic = new AlchemyStatistic("012012");
        alchemyStatistic.increment();

        entityMongoRepository.add(alchemyStatistic);

        final DBObject alchemyAnalysisFound = getAlchemyStatisticFromDB(alchemyStatistic.getId());
        assertThat(alchemyAnalysisFound, notNullValue());
        assertThat(alchemyAnalysisFound.get("month"), is((Object) "012012"));
        assertThat(alchemyAnalysisFound.get("count").toString(), is((Object) "1"));
    }

    @Test
    public void canGetAlchemyStatisticByMonth() {
        entityMongoRepository.add(new AlchemyStatistic("012012"));

        AlchemyStatistic alchemyStatistic = entityMongoRepository.byMonth("012012");

        assertThat(alchemyStatistic, notNullValue());
    }

    private DBObject getAlchemyStatisticFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("alchemystatistic");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private AlchemyStatisticsRepository entityMongoRepository;
}
