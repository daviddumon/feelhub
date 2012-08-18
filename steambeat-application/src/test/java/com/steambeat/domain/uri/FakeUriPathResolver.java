package com.steambeat.domain.uri;

import java.util.List;

public class FakeUriPathResolver extends UriPathResolver {

    @Override
    public List<Uri> resolve(final Uri uri) {
        path.add(uri);
        if (!canonicalUri.isEmpty()) {
            path.add(canonicalUri);
        }
        return path;
    }

    public UriPathResolver thatFind(final Uri canonicalUri) {
        this.canonicalUri = canonicalUri;
        return this;
    }

    private Uri canonicalUri = Uri.empty();
}
