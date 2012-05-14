package com.steambeat.domain.association.tag;

import com.steambeat.domain.association.Identifier;

public class Tag extends Identifier {

    public Tag(final String text) {
        this.text = text.toLowerCase().trim();
    }

    @Override
    public String toString() {
        return text;
    }

    private String text;
}
