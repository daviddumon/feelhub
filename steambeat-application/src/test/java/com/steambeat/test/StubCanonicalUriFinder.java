package com.steambeat.test;

import com.steambeat.domain.subject.webpage.*;

public class StubCanonicalUriFinder extends CanonicalUriFinder {

    @Override
    public Uri find(final Uri uri) {
        if (canonicalUri.isEmpty()) {
            return uri;
        }
        return canonicalUri;
    }

    public CanonicalUriFinder thatFind(final Uri canonicalUri) {
        this.canonicalUri = canonicalUri;
        return this;
    }

    private Uri canonicalUri = Uri.empty();
}
