package com.feelhub.repositories;

import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.ftp.FtpTopic;
import com.feelhub.domain.topic.geo.GeoTopic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.world.WorldTopic;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.UUID;

public class TopicMongoRepository extends BaseMongoRepository<Topic> implements TopicRepository {

    public TopicMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public WorldTopic getWorldTopic() {
        final Criteria criteria = getSession().createCriteria(WorldTopic.class);
        return (WorldTopic) extractOne(criteria);
    }

    @Override
    public GeoTopic getGeoTopic(final UUID id) {
        final Criteria criteria = getSession().createCriteria(GeoTopic.class);
        criteria.add(Restrictions.equals("_id", id));
        return (GeoTopic) extractOne(criteria);
    }

    @Override
    public HttpTopic getHttpTopic(final UUID id) {
        final Criteria criteria = getSession().createCriteria(HttpTopic.class);
        criteria.add(Restrictions.equals("_id", id));
        return (HttpTopic) extractOne(criteria);
    }

    @Override
    public RealTopic getRealTopic(final UUID id) {
        final Criteria criteria = getSession().createCriteria(RealTopic.class);
        criteria.add(Restrictions.equals("_id", id));
        return (RealTopic) extractOne(criteria);
    }

    @Override
    public FtpTopic getFtpTopic(final UUID id) {
        final Criteria criteria = getSession().createCriteria(FtpTopic.class);
        criteria.add(Restrictions.equals("_id", id));
        return (FtpTopic) extractOne(criteria);
    }

    @Override
    public Topic getCurrentTopic(final UUID id) {
        Topic topic = get(id);
        if (topic != null) {
            while (!topic.getCurrentId().equals(topic.getId())) {
                topic = get(topic.getCurrentId());
            }
        }
        return topic;
    }
}
