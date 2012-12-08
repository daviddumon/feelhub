package com.feelhub.domain.topic;

import com.feelhub.domain.Repository;
import com.feelhub.domain.topic.unusable.WorldTopic;
import com.feelhub.domain.topic.usable.geo.GeoTopic;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.domain.topic.usable.web.WebTopic;

import java.util.UUID;

public interface TopicRepository extends Repository<Topic> {

    GeoTopic getGeoTopic(final UUID id);

    WebTopic getWebTopic(final UUID id);

    RealTopic getRealTopic(final UUID id);

    WorldTopic getWorldTopic();

    Topic getCurrentTopic(final UUID id);
}
