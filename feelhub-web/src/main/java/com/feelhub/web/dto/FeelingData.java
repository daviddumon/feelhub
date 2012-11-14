package com.feelhub.web.dto;

import com.feelhub.domain.feeling.Feeling;

import java.util.*;

public class FeelingData {

    public FeelingData(final Feeling feeling, final List<KeywordData> keywordDatas) {
        this.id = feeling.getId();
        this.text = feeling.getText();
        this.languageCode = feeling.getLanguageCode();
        this.userId = feeling.getUserId();
        this.keywordDatas = keywordDatas;
    }

    public List<KeywordData> getKeywordDatas() {
        return keywordDatas;
    }

    public String getText() {
        return text;
    }

    public UUID getId() {
        return id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getUserId() {
        return userId;
    }

    private final UUID id;
    private final String text;
    private final List<KeywordData> keywordDatas;
    private final String languageCode;
    private final String userId;
}
