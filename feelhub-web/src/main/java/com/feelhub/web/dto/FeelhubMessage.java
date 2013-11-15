package com.feelhub.web.dto;

import com.feelhub.domain.feeling.FeelingValue;

public class FeelhubMessage {

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(final String feeling) {
        this.feeling = feeling;
    }

    public int getSecondTimer() {
        return secondTimer;
    }

    public void setSecondTimer(final int secondTimer) {
        this.secondTimer = secondTimer;
    }

    public String toJSON() {
        final StringBuilder json = new StringBuilder();
        json.append("{\"feeling\":\"");
        json.append(feeling);
        json.append("\",\"text\":\"");
        json.append(text);
        json.append("\",\"timer\":");
        json.append(secondTimer);
        json.append("}");
        return json.toString();
    }

    public static FeelhubMessage getActivationMessage() {
        final FeelhubMessage feelhubMessage = new FeelhubMessage();
        feelhubMessage.setFeeling(FeelingValue.happy.toString());
        feelhubMessage.setText("Your account has been activated!");
        feelhubMessage.setSecondTimer(3);
        return feelhubMessage;
    }

    public static FeelhubMessage getErrorMessage() {
        final FeelhubMessage feelhubMessage = new FeelhubMessage();
        feelhubMessage.setFeeling(FeelingValue.sad.toString());
        feelhubMessage.setText("There was a disturbance in the Force!");
        feelhubMessage.setSecondTimer(3);
        return feelhubMessage;
    }

    public static FeelhubMessage getWelcomeMessage() {
        final FeelhubMessage feelhubMessage = new FeelhubMessage();
        feelhubMessage.setFeeling(FeelingValue.happy.toString());
        feelhubMessage.setSecondTimer(3);
        feelhubMessage.setText("Welcome to Feelhub! We hope you will enjoy it :)");
        return feelhubMessage;
    }

    public static FeelhubMessage getWelcomeBackMessage() {
        final FeelhubMessage feelhubMessage = new FeelhubMessage();
        feelhubMessage.setFeeling(FeelingValue.happy.toString());
        feelhubMessage.setSecondTimer(3);
        feelhubMessage.setText("Welcome back!");
        return feelhubMessage;
    }

    private String text;
    private String feeling;
    private int secondTimer = 0;
}