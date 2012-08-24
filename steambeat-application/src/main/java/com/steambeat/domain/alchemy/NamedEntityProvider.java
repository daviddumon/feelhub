package com.steambeat.domain.alchemy;

import java.util.List;

public interface NamedEntityProvider {

    List<NamedEntity> entitiesFor(String uri);
}
