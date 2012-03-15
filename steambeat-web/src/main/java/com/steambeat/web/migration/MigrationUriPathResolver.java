package com.steambeat.web.migration;

import com.steambeat.domain.analytics.identifiers.uri.*;

import java.util.List;

public class MigrationUriPathResolver extends UriPathResolver {

    @Override
    public List<Uri> resolve(final Uri uri) {
        path.add(uri);
        return path;
    }
}
