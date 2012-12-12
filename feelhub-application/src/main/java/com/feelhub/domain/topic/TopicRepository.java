package com.feelhub.domain.topic;

import com.feelhub.domain.Repository;
import com.feelhub.domain.topic.geo.GeoTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.web.WebTopic;
import com.feelhub.domain.topic.world.WorldTopic;

import java.util.UUID;

public interface TopicRepository extends Repository<Topic> {

    GeoTopic getGeoTopic(final UUID id);

    WebTopic getWebTopic(final UUID id);

    RealTopic getRealTopic(final UUID id);

    WorldTopic getWorldTopic();

    Topic getCurrentTopic(final UUID id);
}
