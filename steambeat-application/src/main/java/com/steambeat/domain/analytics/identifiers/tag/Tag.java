package com.steambeat.domain.analytics.identifiers.tag;

import com.steambeat.domain.analytics.identifiers.Identifier;

public class Tag extends Identifier {

    public Tag(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    private String text;
}
