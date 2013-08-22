package com.feelhub.analytic.user;

import java.text.*;
import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
public class ActiveUserDailyBehavior {


    public Date getDate() {
        try {
            return new SimpleDateFormat("yyyyMd").parse(_id);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public long getTotalActives() {
        return totalActives;
    }

    public long getTotalFeelings() {
        return totalFeelings;
    }

    public long getTotalTopics() {
        return totalTopics;
    }

    public long getPostFeeling() {
        return postFeeling;
    }

    public long getPostTopic() {
        return postTopic;
    }

    public long postTopic;
    public String _id;
    public long totalActives;
    public long totalFeelings;
    public long totalTopics;
    public long postFeeling;
}
