package com.steambeat.domain.alchemy;

import com.steambeat.domain.subject.uri.Uri;

import java.util.List;

public interface NamedEntityProvider {

    List<NamedEntity> entitiesFor(Uri uri);
}
