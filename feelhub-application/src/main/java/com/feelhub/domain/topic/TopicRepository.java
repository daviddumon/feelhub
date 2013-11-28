package com.feelhub.domain.topic;

import com.feelhub.domain.Repository;
import com.feelhub.domain.topic.ftp.FtpTopic;
import com.feelhub.domain.topic.geo.GeoTopic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.world.WorldTopic;

import java.util.List;
import java.util.UUID;

public interface TopicRepository extends Repository<Topic> {

    GeoTopic getGeoTopic(final UUID id);

    HttpTopic getHttpTopic(final UUID id);

    RealTopic getRealTopic(final UUID id);

    FtpTopic getFtpTopic(final UUID id);

    WorldTopic getWorldTopic();

    Topic getCurrentTopic(final UUID id);

    List<Topic> findWithoutThumbnail();
}
