package com.feelhub.repositories;

import com.feelhub.domain.media.*;
import com.google.common.collect.Lists;
import org.mongolink.MongoSession;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class MediaMongoRepository extends BaseMongoRepository<Media> implements MediaRepository {

    public MediaMongoRepository(final MongoSession mongoSession) {
        super(mongoSession);
    }

    @Override
    public Media lookUp(final UUID fromId, final UUID toId) {
        final Criteria criteria = getSession().createCriteria(Media.class);
        criteria.add(Restrictions.equals("fromId", fromId));
        criteria.add(Restrictions.equals("toId", toId));
        return extractOne(criteria);
    }

    @Override
    public List<Media> containingTopicId(final UUID topicId) {
        // There is no need to check for duplicates as long as from-from or to-to is forbidden
        // and obviously, topicId is from in one, an to in the another ^
        final List<Media> mediaList = Lists.newArrayList();
        mediaList.addAll(getListWithFromIdEqualsTopicId(topicId));
        mediaList.addAll(getListWithToIdEqualsTopicId(topicId));
        return mediaList;
    }

    private List<Media> getListWithFromIdEqualsTopicId(final UUID topicId) {
        final Criteria fromCriteria = getSession().createCriteria(Media.class);
        fromCriteria.add(Restrictions.equals("fromId", topicId));
        return fromCriteria.list();
    }

    private List<Media> getListWithToIdEqualsTopicId(final UUID topicId) {
        final Criteria fromCriteria = getSession().createCriteria(Media.class);
        fromCriteria.add(Restrictions.equals("toId", topicId));
        return fromCriteria.list();
    }
}
