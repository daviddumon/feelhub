package com.feelhub.analytic.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
public class NewUserDailyBehavior {

    public long getTotalSignups() {
        return totalSignups;
    }

    public long getTotalLogins() {
        return totalLogins;
    }

    public long getTotalFeelings() {
        return totalFeelings;
    }

    public long getTotalTopics() {
        return totalTopics;
    }

    public long getUniqueLogins() {
        return uniqueLogins;
    }

    public long getPostFeeling() {
        return postFeeling;
    }

    public long getPostTopic() {
        return postTopic;
    }

    public Date getDate() {
        try {
            return new SimpleDateFormat("yyyyMd").parse(_id);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public double getPercentageLogins() {
        if(totalSignups == 0) {
            return 0;
        }
        return (uniqueLogins * 100) / totalSignups;
    }

    public double getPercentageFeeling() {
        if(totalSignups == 0) {
            return 0;
        }
        return (postFeeling * 100) / totalSignups;
    }

    public double getPercentageTopic() {
        if(totalSignups == 0) {
            return 0;
        }
        return (postTopic * 100) / totalSignups;
    }

    public String _id;
    public long totalSignups;
    public long totalLogins;
    public long totalFeelings;
    public long totalTopics;
    public long uniqueLogins;
    public long postFeeling;
    public long postTopic;
}
