package com.feelhub.repositories.fakeRepositories;

import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.ftp.FtpTopic;
import com.feelhub.domain.topic.geo.GeoTopic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.world.WorldTopic;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;

public class FakeTopicRepository extends FakeRepository<Topic> implements TopicRepository {

    @Override
    public GeoTopic getGeoTopic(final UUID id) {
        return (GeoTopic) Iterables.find(getAll(), new Predicate<Topic>() {
            @Override
            public boolean apply(final Topic input) {
                return input.getId().equals(id);
            }
        });
    }

    @Override
    public HttpTopic getHttpTopic(final UUID id) {
        return (HttpTopic) Iterables.find(getAll(), new Predicate<Topic>() {
            @Override
            public boolean apply(final Topic input) {
                return input.getId().equals(id);
            }
        });
    }

    @Override
    public RealTopic getRealTopic(final UUID id) {
        return (RealTopic) Iterables.find(getAll(), new Predicate<Topic>() {
            @Override
            public boolean apply(final Topic input) {
                return input.getId().equals(id);
            }
        });
    }

    @Override
    public FtpTopic getFtpTopic(final UUID id) {
        return (FtpTopic) Iterables.find(getAll(), new Predicate<Topic>() {
            @Override
            public boolean apply(final Topic input) {
                return input.getId().equals(id);
            }
        });
    }

    @Override
    public WorldTopic getWorldTopic() {
        try {
            return (WorldTopic) Iterables.find(getAll(), new Predicate<Topic>() {

                @Override
                public boolean apply(final Topic input) {
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
        if (topic != null) {
            while (!topic.getCurrentId().equals(topic.getId())) {
                topic = get(topic.getCurrentId());
            }
        }
        return topic;
    }

    @Override
    public List<Topic> findWithoutThumbnail() {
        return Lists.newArrayList(Iterables.filter(getAll(), new Predicate<Topic>() {
            @Override
            public boolean apply(Topic input) {
                return input.getThumbnail() == null;
            }
        }));
    }
}
