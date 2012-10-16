package com.steambeat.web.dto;

import java.util.List;

public class OpinionData {

    public OpinionData(final String text, final List<ReferenceData> referenceDatas) {
        this.text = text;
        this.referenceDatas = referenceDatas;
    }

    public String getText() {
        return text;
    }

    public List<ReferenceData> getReferenceDatas() {
        return referenceDatas;
    }

    private final String text;
    private final List<ReferenceData> referenceDatas;
}
