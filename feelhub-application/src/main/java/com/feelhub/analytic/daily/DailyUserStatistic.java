package com.feelhub.analytic.daily;

import java.util.Date;

public class DailyUserStatistic {

    public Date getDate() {
        return date;
    }

    public int getCreations() {
        return creations;
    }

    public int getActivations() {
        return activations;
    }

    public double getActivationsPercentage() {
        return percentage(activations);

    }

    public double getLoginsPercentage() {
        return percentage(logins);

    }

    private double percentage(int var) {
        return (var * 100) / creations;
    }

    public int getLogins() {
        return logins;
    }

    public String _id;

    public Date date;
    public int creations;
    public int activations;
    public int logins;

}
