package com.steambeat.domain.uri;

import com.google.common.collect.Lists;

import java.util.List;

public class Path {

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (Uri uri : uris) {
            stringBuilder.append("[ " + uri.toString() + " ]");
        }
        return stringBuilder.toString();
    }

    public int size() {
        return uris.size();
    }

    public Uri get(final int index) {
        return uris.get(index);
    }

    public void add(final Uri uri) {
        uris.add(uri);
    }

    public List<Uri> getUris() {
        return uris;
    }

    private List<Uri> uris = Lists.newArrayList();
}
