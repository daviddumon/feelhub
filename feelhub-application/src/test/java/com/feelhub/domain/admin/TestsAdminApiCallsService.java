package com.feelhub.domain.admin;

import com.feelhub.domain.eventbus.*;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.SystemTime;
import com.google.inject.*;
import org.joda.time.DateTime;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsAdminApiCallsService {

    @Rule
    public SystemTime systemTime = SystemTime.fixed();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        injector.getInstance(AdminApiCallsService.class);
    }

    @Test
    public void canIncrementApiCallsCountWhenNoStatistics() {
        systemTime.set(new DateTime(2012, 1, 21, 12, 30));

        DomainEventBus.INSTANCE.post(ApiCallEvent.alchemy());

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
        statistic.increment(1);
        Repositories.adminStatistics().add(statistic);

        DomainEventBus.INSTANCE.post(ApiCallEvent.alchemy());

        AdminStatistic adminStatistic = Repositories.adminStatistics().getAll().get(0);
        assertThat(adminStatistic.getCount()).isEqualTo(2);
    }

    @Test
    public void canIncrementApiCallsCountWithAValue() {
        DomainEventBus.INSTANCE.post(ApiCallEvent.microsoftTranslate(12));

        AdminStatistic adminStatistic = Repositories.adminStatistics().getAll().get(0);
        assertThat(adminStatistic.getCount()).isEqualTo(12);
    }
}
