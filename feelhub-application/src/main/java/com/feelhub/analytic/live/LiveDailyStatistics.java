package com.feelhub.analytic.live;

import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
public class LiveDailyStatistics {

    public Date getDate() {
        return date;
    }

    public int getSignups() {
        return signups;
    }

    public int getLogins() {
        return logins;
    }

    public int getHttpTopics() {
        return httpTopics;
    }

    public int getRealTopics() {
        return realTopics;
    }

    public int getTopics() {
        return topics;
    }

    public int getFeelings() {
        return feelings;
    }

    public String _id;
    public Date date;
    public int signups;
    public int logins;
    public int httpTopics;
    public int realTopics;
    public int topics;
    public int feelings;
}
