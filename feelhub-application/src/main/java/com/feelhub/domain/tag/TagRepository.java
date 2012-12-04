package com.feelhub.domain.tag;

import com.feelhub.domain.Repository;

import java.util.*;

public interface TagRepository extends Repository<Tag> {

    List<Tag> forTopicId(final UUID topicId);
}
