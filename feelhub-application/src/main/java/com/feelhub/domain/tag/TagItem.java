package com.feelhub.domain.tag;

import java.util.UUID;

public class TagItem {

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(final String languageCode) {
        this.languageCode = languageCode;
    }

    private UUID id;
    private String languageCode;
}
