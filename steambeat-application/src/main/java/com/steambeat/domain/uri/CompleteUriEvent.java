package com.steambeat.domain.uri;

import com.steambeat.domain.reference.ReferencesToChangeEvent;

public class CompleteUriEvent extends ReferencesToChangeEvent {

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append("PathEvent ");
        stringBuilder.append(keywords.size());
        return stringBuilder.toString();
    }
}
