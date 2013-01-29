package com.feelhub.domain.admin;

import com.feelhub.repositories.Repositories;
import org.joda.time.DateTime;
import org.joda.time.format.*;

public class AdminStatisticCallsCounter {
    public void increment(Api api) {
        String month = currentMonth();
        AdminStatistic adminStatistic = Repositories.adminStatistics().byMonthAndApi(month, api);
        if (adminStatistic == null) {
            adminStatistic = new AdminStatistic(month, api);
            Repositories.adminStatistics().add(adminStatistic);
        }
        adminStatistic.increment();
    }

    private String currentMonth() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MMyyyy");
        return formatter.print(new DateTime());
    }
}
