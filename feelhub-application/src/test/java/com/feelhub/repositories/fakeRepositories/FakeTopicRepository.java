package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.unusable.WorldTopic;
import com.feelhub.domain.topic.usable.geo.GeoTopic;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.domain.topic.usable.web.WebTopic;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Nullable;

import java.util.UUID;

public class FakeTopicRepository extends FakeRepository<Topic> implements TopicRepository {

    @Override
    public GeoTopic getGeoTopic(final UUID id) {
        return (GeoTopic) Iterables.find(getAll(), new Predicate<Topic>() {
            @Override
            public boolean apply(@Nullable final Topic input) {
                return input.getId().equals(id);
            }
        });
    }

    @Override
    public WebTopic getWebTopic(final UUID id) {
        return (WebTopic) Iterables.find(getAll(), new Predicate<Topic>() {
            @Override
            public boolean apply(@Nullable final Topic input) {
                return input.getId().equals(id);
            }
        });
    }

    @Override
    public RealTopic getRealTopic(final UUID id) {
        return (RealTopic) Iterables.find(getAll(), new Predicate<Topic>() {
            @Override
            public boolean apply(@Nullable final Topic input) {
                return input.getId().equals(id);
            }
        });
    }

    @Override
    public WorldTopic getWorldTopic() {
        try {
            return (WorldTopic) Iterables.find(getAll(), new Predicate<Topic>() {

                @Override
                public boolean apply(@Nullable final Topic input) {
                    return input.getClass().equals(WorldTopic.class);
                }
            });
        } catch (Exception e) {
            return null;
        }
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
