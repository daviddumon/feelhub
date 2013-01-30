package com.feelhub.repositories;

import com.feelhub.domain.admin.AdminStatistic;
import com.feelhub.domain.admin.AdminStatisticsRepository;
import com.feelhub.domain.admin.Api;
import com.feelhub.test.SystemTime;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestsAdminStatisticsMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        entityMongoRepository = Repositories.adminStatistics();
    }

    @Test
    public void canPersistAdminStatistic() {
        AdminStatistic adminStatistic = new AdminStatistic("012012", Api.Alchemy);
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
        AdminStatistic alchemyStat = new AdminStatistic("012012", Api.Alchemy);
        entityMongoRepository.add(alchemyStat);
        entityMongoRepository.add(new AdminStatistic("012012", Api.BingSearch));

        AdminStatistic adminStatistic = entityMongoRepository.byMonthAndApi("012012", Api.Alchemy);

        assertThat(adminStatistic, notNullValue());
        assertThat(adminStatistic.getId(), is(alchemyStat.getId()));
    }

    @Test
    public void canGetAllAdminStatisticsByApi() {
        AdminStatistic anAlchemyStat = new AdminStatistic("012012", Api.Alchemy);
        entityMongoRepository.add(anAlchemyStat);
        AdminStatistic anotherAlchemyStat = new AdminStatistic("022012", Api.Alchemy);
        entityMongoRepository.add(anotherAlchemyStat);
        entityMongoRepository.add(new AdminStatistic("022012", Api.BingSearch));

        List<AdminStatistic> adminStatistics = entityMongoRepository.getAll(Api.Alchemy);

        assertThat(adminStatistics.size(), is(2));
        assertThat(adminStatistics, hasItem(anAlchemyStat));
        assertThat(adminStatistics, hasItem(anotherAlchemyStat));
    }

    private DBObject getAlchemyStatisticFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("adminstatistic");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private AdminStatisticsRepository entityMongoRepository;
}
