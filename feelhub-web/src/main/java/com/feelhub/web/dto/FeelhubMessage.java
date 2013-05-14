package com.feelhub.web.dto;

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

    private String text;
    private String feeling;
    private int secondTimer = 0;
}