package com.feelhub.domain.related;

import com.feelhub.domain.Repository;

import java.util.*;

public interface RelatedRepository extends Repository<Related> {

    Related lookUp(final UUID fromId, final UUID toId);

    List<Related> containingTopicId(final UUID topicId);

    List<Related> forTopicId(final UUID topicId);
}
