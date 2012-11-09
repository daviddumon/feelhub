package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.topic.*;

public class FakeTopicRepository extends FakeRepository<Topic> implements TopicRepository {

    @Override
    public Topic getActive(Object id) {
        Topic topic = get(id);
        while (!topic.isActive() && !topic.getCurrentTopicId().equals(id)) {
            id = topic.getCurrentTopicId();
            topic = get(id);
        }
        return topic;
    }
}
