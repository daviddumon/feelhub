package com.steambeat.domain.alchemy;

import com.steambeat.domain.subject.webpage.WebPage;

import java.util.List;

public interface NamedEntityProvider {

    List<NamedEntity> entitiesFor(WebPage webpage);
}
