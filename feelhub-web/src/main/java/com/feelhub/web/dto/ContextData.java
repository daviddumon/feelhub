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

    public String getThumbnailSmall() {
        return thumbnailSmall;
    }

    public void setThumbnailSmall(final String thumbnailSmall) {
        if (thumbnailSmall != null) {
            this.thumbnailSmall = thumbnailSmall;
        }
    }

    private String value = "";
    private UUID id;
    private String thumbnailSmall = "";
}
