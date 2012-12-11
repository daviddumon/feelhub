package com.feelhub.repositories.mapping;

import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.unusable.WorldTopic;
import com.feelhub.domain.topic.usable.geo.GeoTopic;
import com.feelhub.domain.topic.usable.real.RealTopic;
import com.feelhub.domain.topic.usable.web.WebTopic;
import org.mongolink.domain.mapper.*;

public class TopicMapping extends EntityMap<Topic> {

    public TopicMapping() {
        super(Topic.class);
    }

    @Override
    protected void map() {
        id(element().getId()).natural();
        property(element().getCurrentId());
        property(element().getCreationDate());
        property(element().getLastModificationDate());

        subclass(new SubclassMap<RealTopic>(RealTopic.class) {

            @Override
            protected void map() {
                property(element().getTypeValue());
                property(element().getUserId());
                map(element().getNames());
                map(element().getDescriptions());
                collection(element().getSubTypes());
            }
        });

        subclass(new SubclassMap<WebTopic>(WebTopic.class) {

            @Override
            protected void map() {
                property(element().getTypeValue());
                property(element().getUserId());
                map(element().getNames());
                map(element().getDescriptions());
                collection(element().getUrls());
            }
        });

        subclass(new SubclassMap<GeoTopic>(GeoTopic.class) {

            @Override
            protected void map() {
                property(element().getTypeValue());
                property(element().getUserId());
                map(element().getNames());
                map(element().getDescriptions());
            }
        });

        subclass(new SubclassMap<WorldTopic>(WorldTopic.class) {

            @Override
            protected void map() {

            }
        });
    }
}
