package com.feelhub.domain.feeling;

import com.feelhub.domain.Repository;

import java.util.*;

public interface FeelingRepository extends Repository<Feeling> {

    List<Feeling> forTopicId(final UUID topicId);

    List<Feeling> forTopicIdUserIdAndFeelingValue(final UUID topicId, final UUID userId, final FeelingValue feelingValue);
}
