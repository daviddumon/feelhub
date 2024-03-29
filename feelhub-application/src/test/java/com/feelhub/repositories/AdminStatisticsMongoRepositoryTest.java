package com.feelhub.repositories;

import com.feelhub.domain.admin.*;
import com.feelhub.test.SystemTime;
import com.mongodb.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class AdminStatisticsMongoRepositoryTest extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        entityMongoRepository = Repositories.adminStatistics();
    }

    @Test
    public void canPersistAdminStatistic() {
        final AdminStatistic adminStatistic = new AdminStatistic("012012", Api.Alchemy);
        adminStatistic.increment(1);

        entityMongoRepository.add(adminStatistic);

        final DBObject alchemyAnalysisFound = getAlchemyStatisticFromDB(adminStatistic.getId());
        assertThat(alchemyAnalysisFound, notNullValue());
        assertThat(alchemyAnalysisFound.get("month"), is((Object) "012012"));
        assertThat(alchemyAnalysisFound.get("count").toString(), is((Object) "1"));
        assertThat(alchemyAnalysisFound.get("api").toString(), is((Object) "Alchemy"));
    }

    @Test
    public void canGetAdminStatisticByMonthAndApi() {
        final AdminStatistic alchemyStat = new AdminStatistic("012012", Api.Alchemy);
        entityMongoRepository.add(alchemyStat);
        entityMongoRepository.add(new AdminStatistic("012012", Api.BingSearch));

        final AdminStatistic adminStatistic = entityMongoRepository.byMonthAndApi("012012", Api.Alchemy);

        assertThat(adminStatistic, notNullValue());
        assertThat(adminStatistic.getId(), is(alchemyStat.getId()));
    }

    @Test
    public void canGetAllAdminStatisticsByApi() {
        final AdminStatistic anAlchemyStat = new AdminStatistic("012012", Api.Alchemy);
        entityMongoRepository.add(anAlchemyStat);
        final AdminStatistic anotherAlchemyStat = new AdminStatistic("022012", Api.Alchemy);
        entityMongoRepository.add(anotherAlchemyStat);
        entityMongoRepository.add(new AdminStatistic("022012", Api.BingSearch));

        final List<AdminStatistic> adminStatistics = entityMongoRepository.getAll(Api.Alchemy);

        assertThat(adminStatistics.size(), is(2));
        assertThat(adminStatistics, hasItem(anAlchemyStat));
        assertThat(adminStatistics, hasItem(anotherAlchemyStat));
    }

    private DBObject getAlchemyStatisticFromDB(final Object id) {
        final DBCollection collection = getMongo().getCollection("adminstatistic");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private AdminStatisticsRepository entityMongoRepository;
}
