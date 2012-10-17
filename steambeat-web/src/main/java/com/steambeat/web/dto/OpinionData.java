package com.steambeat.web.dto;

import java.util.*;

public class OpinionData {

    public OpinionData(final UUID id, final String text, final List<ReferenceData> referenceDatas) {
        this.id = id;
        this.text = text;
        this.referenceDatas = referenceDatas;
    }

    public String getText() {
        return text;
    }

    public List<ReferenceData> getReferenceDatas() {
        return referenceDatas;
    }

    public UUID getId() {
        return id;
    }

    private UUID id;
    private final String text;
    private final List<ReferenceData> referenceDatas;
}
