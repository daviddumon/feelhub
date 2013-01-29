package com.feelhub.domain.media;

import com.feelhub.domain.Repository;

import java.util.*;

public interface MediaRepository extends Repository<Media> {

    Media lookUp(final UUID fromId, final UUID toId);

    List<Media> containingTopicId(final UUID topicId);
}
