package com.feelhub.web.dto;

import java.util.UUID;

public class ContextData {

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(final String thumbnail) {
        if (thumbnail != null) {
            this.thumbnail = thumbnail;
        }
    }

    private String value = "";
    private UUID id;
    private String thumbnail = "";
}
