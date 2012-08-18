package com.steambeat.domain.alchemy;

import com.steambeat.domain.uri.Uri;

import java.util.List;

public interface NamedEntityProvider {

    List<NamedEntity> entitiesFor(Uri uri);
}
