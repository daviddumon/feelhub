package com.feelhub.domain.admin;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.Repositories;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.format.*;

public class AdminApiCallsService {

    @Inject
    public AdminApiCallsService() {
        DomainEventBus.INSTANCE.register(this);
    }

    @Subscribe
    public void onApiCall(final ApiCallEvent event) {
        increment(event.getApi(), event.getIncrement());
    }

    public void increment(Api api, int increment) {
        String month = currentMonth();
        AdminStatistic adminStatistic = Repositories.adminStatistics().byMonthAndApi(month, api);
        if (adminStatistic == null) {
            adminStatistic = new AdminStatistic(month, api);
            Repositories.adminStatistics().add(adminStatistic);
        }
        adminStatistic.increment(increment);
    }

    private String currentMonth() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MMyyyy");
        return formatter.print(new DateTime());
    }
}
