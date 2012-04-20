package com.steambeat.domain.analytics.alchemy;

import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.subject.webpage.WebPage;

import java.util.List;

public interface AlchemyEntityProvider {

    List<AlchemyJsonEntity> entitiesFor(WebPage webpage);
}
