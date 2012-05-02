package com.steambeat.web.migration.fake;

import com.steambeat.domain.association.uri.*;

import java.util.List;

public class MigrationUriPathResolver extends UriPathResolver {

    @Override
    public List<Uri> resolve(final Uri uri) {
        path.add(uri);
        return path;
    }
}
