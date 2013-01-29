package com.feelhub.domain.alchemy;

import com.feelhub.domain.admin.AdminStatistic;
import com.feelhub.domain.admin.AdminStatisticCallsCounter;
import com.feelhub.domain.admin.Api;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import org.joda.time.DateTime;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TestsAlchemyLink {

    @Rule
    public SystemTime systemTime = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canIncrementApiCallsCountWhenNoStatistic() {
        systemTime.set(new DateTime(2012, 1, 21, 12, 30));
        FakeAlchemyLink alchemyLink = new FakeAlchemyLink(new AdminStatisticCallsCounter());

        alchemyLink.get(new Uri("http://www.toto.com"));

        assertThat(Repositories.adminStatistics().getAll()).hasSize(1);
        AdminStatistic adminStatistic = Repositories.adminStatistics().getAll().get(0);
        assertThat(adminStatistic.getMonth()).isEqualTo("012012");
        assertThat(adminStatistic.getCount()).isEqualTo(1);
        assertThat(adminStatistic.getApi()).isEqualTo(Api.Alchemy);
    }

    @Test
    public void canIncrementApiCallsCountWhenAStatisticExists() {
        systemTime.set(new DateTime(2012, 1, 21, 12, 30));
        AdminStatistic statistic = new AdminStatistic("012012", Api.Alchemy);
        statistic.increment();
        Repositories.adminStatistics().add(statistic);
        FakeAlchemyLink alchemyLink = new FakeAlchemyLink(new AdminStatisticCallsCounter());

        alchemyLink.get(new Uri("http://www.toto.com"));

        AdminStatistic adminStatistic = Repositories.adminStatistics().getAll().get(0);
        assertThat(adminStatistic.getCount()).isEqualTo(2);
    }
}
