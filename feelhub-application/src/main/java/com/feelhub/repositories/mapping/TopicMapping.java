package com.feelhub.repositories.mapping;

import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.ftp.FtpTopic;
import com.feelhub.domain.topic.geo.GeoTopic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.topic.world.WorldTopic;
import org.mongolink.domain.mapper.*;

public class TopicMapping extends AggregateMap<Topic> {

    public TopicMapping() {
        super(Topic.class);
    }

    @Override
    public void map() {
        id().onProperty(element().getId()).natural();
        property().onProperty(element().getCurrentId());
        property().onProperty(element().getCreationDate());
        property().onProperty(element().getLastModificationDate());
        property().onProperty(element().getUserId());
        map(element().getNames());
        map(element().getDescriptions());
        collection().onProperty(element().getSubTypes());
        collection().onProperty(element().getUris());
        property().onProperty(element().getThumbnail());
        collection().onProperty(element().getThumbnails());
        property().onProperty(element().getHappyFeelingCount());
        property().onProperty(element().getBoredFeelingCount());
        property().onProperty(element().getSadFeelingCount());
        property().onProperty(element().getHasFeelings());

        subclass(new SubclassMap<RealTopic>(RealTopic.class) {

            @Override
            public void map() {
                property().onProperty(element().getTypeValue());
            }
        });

        subclass(new SubclassMap<HttpTopic>(HttpTopic.class) {

            @Override
            public void map() {
                property().onProperty(element().getTypeValue());
                property().onProperty(element().getMediaTypeValue());
                property().onProperty(element().getOpenGraphType());
            }
        });

        subclass(new SubclassMap<GeoTopic>(GeoTopic.class) {

            @Override
            public void map() {
                property().onProperty(element().getTypeValue());
            }
        });

        subclass(new SubclassMap<WorldTopic>(WorldTopic.class) {

            @Override
            public void map() {

            }
        });

        subclass(new SubclassMap<FtpTopic>(FtpTopic.class) {

            @Override
            public void map() {

            }
        });
    }
}
