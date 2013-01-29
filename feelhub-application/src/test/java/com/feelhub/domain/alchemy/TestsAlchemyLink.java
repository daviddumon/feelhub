package com.feelhub.domain.alchemy;

import com.feelhub.domain.admin.AlchemyCallsCounter;
import com.feelhub.domain.admin.AlchemyStatistic;
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
        FakeAlchemyLink alchemyLink = new FakeAlchemyLink(new AlchemyCallsCounter());

        alchemyLink.get(new Uri("http://www.toto.com"));

        assertThat(Repositories.alchemyStatistics().getAll()).hasSize(1);
        AlchemyStatistic alchemyStatistic = Repositories.alchemyStatistics().getAll().get(0);
        assertThat(alchemyStatistic.getMonth()).isEqualTo("012012");
        assertThat(alchemyStatistic.getCount()).isEqualTo(1);
    }

    @Test
    public void canIncrementApiCallsCountWhenAStatisticExists() {
        systemTime.set(new DateTime(2012, 1, 21, 12, 30));
        AlchemyStatistic statistic = new AlchemyStatistic("012012");
        statistic.increment();
        Repositories.alchemyStatistics().add(statistic);
        FakeAlchemyLink alchemyLink = new FakeAlchemyLink(new AlchemyCallsCounter());

        alchemyLink.get(new Uri("http://www.toto.com"));

        AlchemyStatistic alchemyStatistic = Repositories.alchemyStatistics().getAll().get(0);
        assertThat(alchemyStatistic.getCount()).isEqualTo(2);
    }
}
