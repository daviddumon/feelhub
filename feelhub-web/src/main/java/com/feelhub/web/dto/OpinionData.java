package com.feelhub.web.dto;

import com.feelhub.domain.opinion.Opinion;

import java.util.*;

public class OpinionData {

    public OpinionData(final Opinion opinion, final List<ReferenceData> referenceDatas) {
        this.id = opinion.getId();
        this.text = opinion.getText();
        this.languageCode = opinion.getLanguageCode();
        this.userId = opinion.getUserId();
        this.referenceDatas = referenceDatas;
    }

    public List<ReferenceData> getReferenceDatas() {
        return referenceDatas;
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

    private UUID id;
    private final String text;
    private final List<ReferenceData> referenceDatas;
    private String languageCode;
    private final String userId;
}
