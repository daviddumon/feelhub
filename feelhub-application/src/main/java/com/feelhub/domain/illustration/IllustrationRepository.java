package com.feelhub.domain.illustration;

import com.feelhub.domain.Repository;

import java.util.*;

public interface IllustrationRepository extends Repository<Illustration> {

    public List<Illustration> forTopicId(final UUID topicId);
}
