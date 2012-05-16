package com.steambeat.domain.alchemy;

import com.steambeat.domain.subject.webpage.WebPage;

import java.util.List;

public interface ANamedEntityProvider {

    List<NamedEntity> entitiesFor(WebPage webpage);
}
