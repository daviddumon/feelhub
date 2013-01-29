package com.feelhub.domain.admin;

import com.feelhub.repositories.Repositories;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AlchemyCallsCounter {
    public void increment() {
        String month = currentMonth();
        AlchemyStatistic alchemyStatistic = Repositories.alchemyStatistics().byMonth(month);
        if (alchemyStatistic == null) {
            alchemyStatistic = new AlchemyStatistic(month);
            Repositories.alchemyStatistics().add(alchemyStatistic);
        }
        alchemyStatistic.increment();
    }

    private String currentMonth() {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("MMyyyy");
        return formatter.print(new DateTime());
    }
}
