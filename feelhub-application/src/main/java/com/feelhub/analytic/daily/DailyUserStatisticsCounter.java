package com.feelhub.analytic.daily;

import org.joda.time.DateMidnight;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.Update;

import javax.inject.Inject;

public class DailyUserStatisticsCounter {

    @Inject
    public DailyUserStatisticsCounter(Jongo jongo) {
        this.jongo = jongo;
    }

    public void incrementActivationCount() {
        update().with("{$inc : {activations:1}}");
    }

    public void incrementUserCreation() {
        update().with("{$inc : {creations:1}}");
    }

    public void incrementLoginCount() {
        update().with("{$inc : {logins:1}}");
    }

    private Update update() {
        MongoCollection dailystatistic = jongo.getCollection("dailyuserstatistic");
        dailystatistic.ensureIndex("{date : 1}");
        return dailystatistic.update("{date:#}", new DateMidnight().toDate()).upsert();
    }

    private Jongo jongo;
}
