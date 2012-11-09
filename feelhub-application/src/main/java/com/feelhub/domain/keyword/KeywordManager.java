package com.feelhub.domain.keyword;

import com.feelhub.domain.topic.TopicPatch;
import com.feelhub.repositories.Repositories;

import java.util.*;

public class KeywordManager {

    public void merge(final TopicPatch topicPatch) {
        for (final UUID oldTopicId : topicPatch.getOldTopicIds()) {
            final List<Keyword> keywords = Repositories.keywords().forTopicId(oldTopicId);
            for (final Keyword keyword : keywords) {
                keyword.setTopicId(topicPatch.getNewTopicId());
            }
        }
    }
}
