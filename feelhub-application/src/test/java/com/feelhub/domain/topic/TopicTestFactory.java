package com.feelhub.domain.topic;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.ftp.FtpTopic;
import com.feelhub.domain.topic.geo.*;
import com.feelhub.domain.topic.http.*;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.topic.real.*;
import com.feelhub.domain.topic.world.WorldTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.test.TestFactories;
import org.restlet.data.MediaType;

import java.util.UUID;

public class TopicTestFactory {

    public RealTopic newCompleteRealTopic() {
        return newCompleteRealTopic("name");
    }

    public RealTopic newCompleteRealTopic(final String name) {
        return newCompleteRealTopic(name, RealTopicType.Automobile);
    }

    public RealTopic newCompleteRealTopic(final String name, final RealTopicType type) {
        final RealTopic realTopic = new RealTopic(UUID.randomUUID(), type);
        realTopic.addName(FeelhubLanguage.reference(), name + "-reference");
        realTopic.addName(FeelhubLanguage.fromCode("fr"), name + "-fr");
        realTopic.addDescription(FeelhubLanguage.reference(), "description-reference");
        realTopic.addDescription(FeelhubLanguage.fromCode("fr"), "description-fr");
        realTopic.addSubType("subtype1");
        realTopic.addSubType("subtype2");
        realTopic.setUserId(TestFactories.users().createFakeActiveUser("mail@mail.com").getId());
        realTopic.setThumbnail("thumbnail");
        Repositories.topics().add(realTopic);
        return realTopic;
    }

    public HttpTopic newCompleteHttpTopic() {
        final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID());
        httpTopic.setType(HttpTopicType.Article);
        httpTopic.setMediaType(MediaType.TEXT_HTML);
        httpTopic.addUri(new Uri("http://www.fakeurl.com"));
        httpTopic.addDescription(FeelhubLanguage.reference(), "description-reference");
        httpTopic.addName(FeelhubLanguage.reference(), "name-reference");
        httpTopic.setUserId(TestFactories.users().createFakeActiveUser("mail@mail.com").getId());
        httpTopic.setOpenGraphType("article");
        httpTopic.setThumbnail("thumbnail");
        Repositories.topics().add(httpTopic);
        return httpTopic;
    }

    public FtpTopic newSimpleFtpTopic() {
        final FtpTopic ftpTopic = new FtpTopic(UUID.randomUUID());
        Repositories.topics().add(ftpTopic);
        return ftpTopic;
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

    public HttpTopic newSimpleHttpTopic(final HttpTopicType type) {
        final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID());
        httpTopic.setType(type);
        Repositories.topics().add(httpTopic);
        return httpTopic;
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

    public HttpTopic newMediaTopic() {
        final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID());
        httpTopic.setType(HttpTopicType.Image);
        Repositories.topics().add(httpTopic);
        return httpTopic;
    }
}
