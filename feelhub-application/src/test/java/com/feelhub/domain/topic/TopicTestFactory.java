package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.geo.*;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.web.*;
import com.feelhub.domain.topic.world.WorldTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;

import java.util.UUID;

public class TopicTestFactory {

    public RealTopic newCompleteRealTopic() {
        return newCompleteRealTopic("name");
    }

    public RealTopic newCompleteRealTopic(final String name) {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), RealTopicType.Automobile);
        realTopic.addName(FeelhubLanguage.reference(), name + "-reference");
        realTopic.addName(FeelhubLanguage.fromCode("fr"), name + "-fr");
        realTopic.addDescription(FeelhubLanguage.reference(), "description-reference");
        realTopic.addDescription(FeelhubLanguage.fromCode("fr"), "description-fr");
        realTopic.addSubType("subtype1");
        realTopic.addSubType("subtype2");
        realTopic.setUserId(TestFactories.users().createFakeActiveUser("mail@mail.com").getId());
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    public WebTopic newCompleteWebTopic() {
        final WebTopic webTopic = new WebTopic(UUID.randomUUID(), WebTopicType.Article);
        webTopic.addUrl("http://www.fakeurl.com");
        webTopic.addDescription(FeelhubLanguage.reference(), "description-reference");
        webTopic.addName(FeelhubLanguage.reference(), "name-reference");
        webTopic.setUserId(TestFactories.users().createFakeActiveUser("mail@mail.com").getId());
        Repositories.topics().add(webTopic);
        return webTopic;
    }

    public GeoTopic newCompleteGeoTopic() {
        final GeoTopic geoTopic = new GeoTopic(UUID.randomUUID(), GeoTopicType.Coords);
        geoTopic.addDescription(FeelhubLanguage.reference(), "description-reference");
        geoTopic.addName(FeelhubLanguage.reference(), "name-reference");
        geoTopic.setUserId(TestFactories.users().createFakeActiveUser("mail@mail.com").getId());
        Repositories.topics().add(geoTopic);
        return geoTopic;
    }

    public RealTopic newSimpleRealTopic(final RealTopicType type) {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), type);
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    public WebTopic newSimpleWebTopic(final WebTopicType type) {
        final WebTopic webTopic = new WebTopic(UUID.randomUUID(), type);
        Repositories.topics().add(webTopic);
        return webTopic;
    }

    public GeoTopic newSimpleGeoTopic(final GeoTopicType type) {
        final GeoTopic geoTopic = new GeoTopic(UUID.randomUUID(), type);
        Repositories.topics().add(geoTopic);
        return geoTopic;
    }

    public RealTopic newRealTopicWithoutNames(final RealTopicType type) {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), type);
        realTopic.setUserId(TestFactories.users().createFakeActiveUser("mail@mail.com").getId());
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    public WorldTopic newWorldTopic() {
        final WorldTopic worldTopic = new WorldTopic(UUID.randomUUID());
        Repositories.topics().add(worldTopic);
        return worldTopic;
    }
}
