package com.feelhub.domain.relation;

import com.feelhub.domain.Repository;
import com.feelhub.domain.relation.related.Related;

import java.util.*;

public interface RelationRepository extends Repository<Relation> {

    Relation lookUpRelated(final UUID fromId, final UUID toId);

    Relation lookUpMedia(final UUID fromId, final UUID toId);

    List<Relation> containingTopicId(final UUID topicId);

    List<Related> relatedForTopicId(final UUID topicId);
}
