package com.feelhub.repositories.mapping;

import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.geo.GeoTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.web.WebTopic;
import com.feelhub.domain.topic.world.WorldTopic;
import org.mongolink.domain.mapper.*;

public class TopicMapping extends AggregateMap<Topic> {

    public TopicMapping() {
        super(Topic.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getCurrentId());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
        property(element().getUserId());
        map(element().getNames());
        map(element().getDescriptions());
        collection(element().getSubTypes());
        collection(element().getUrls());
        property(element().getIllustrationLink());

        subclass(new SubclassMap<RealTopic>(RealTopic.class) {

            @Override
            protected void map() {
                property(element().getTypeValue());
            }
        });

        subclass(new SubclassMap<WebTopic>(WebTopic.class) {

            @Override
            protected void map() {
                property(element().getTypeValue());
            }
        });

        subclass(new SubclassMap<GeoTopic>(GeoTopic.class) {

            @Override
            protected void map() {
                property(element().getTypeValue());
            }
        });

        subclass(new SubclassMap<WorldTopic>(WorldTopic.class) {

            @Override
            protected void map() {

            }
        });
    }
}
