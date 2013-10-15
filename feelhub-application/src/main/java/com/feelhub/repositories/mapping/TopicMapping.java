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
    protected void map() {
        id(element().getId()).natural();
        property(element().getCurrentId());
        property(element().getCreationDate());
        property(element().getLastModificationDate());
        property(element().getUserId());
        map(element().getNames());
        map(element().getDescriptions());
        collection(element().getSubTypes());
        collection(element().getUris());
        property(element().getThumbnail());
        collection(element().getThumbnails());
        property(element().getGoodFeelingCount());
        property(element().getNeutralFeelingCount());
        property(element().getBadFeelingCount());
        property(element().getHasFeelings());

        subclass(new SubclassMap<RealTopic>(RealTopic.class) {

            @Override
            protected void map() {
                property(element().getTypeValue());
            }
        });

        subclass(new SubclassMap<HttpTopic>(HttpTopic.class) {

            @Override
            protected void map() {
                property(element().getTypeValue());
                property(element().getMediaTypeValue());
                property(element().getOpenGraphType());
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

        subclass(new SubclassMap<FtpTopic>(FtpTopic.class) {

            @Override
            protected void map() {

            }
        });
    }
}
