package com.feelhub.repositories;

import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.unusable.WorldTopic;
import com.feelhub.domain.topic.usable.geo.GeoTopic;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.domain.topic.usable.web.WebTopic;
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
        final Criteria criteria = getSession().createCriteria(WorldTopic.class);
        criteria.add(Restrictions.equals("_id", id));
        return (GeoTopic) extractOne(criteria);
    }

    @Override
    public WebTopic getWebTopic(final UUID id) {
        final Criteria criteria = getSession().createCriteria(WorldTopic.class);
        criteria.add(Restrictions.equals("_id", id));
        return (WebTopic) extractOne(criteria);
    }

    @Override
    public RealTopic getRealTopic(final UUID id) {
        final Criteria criteria = getSession().createCriteria(WorldTopic.class);
        criteria.add(Restrictions.equals("_id", id));
        return (RealTopic) extractOne(criteria);
    }

    @Override
    public Topic getCurrentTopic(final UUID id) {
        Topic topic = get(id);
        while (!topic.getCurrentId().equals(topic.getId())) {
            topic = get(topic.getCurrentId());
        }
        return topic;
    }
}
