package com.feelhub.web.dto;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.UnusableTopicTypes;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import com.google.common.collect.Lists;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TopicDataTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void hasId() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().id(realTopic.getId()).build();

        assertThat(topicData.getId()).isEqualTo(realTopic.getId().toString());
    }

    @Test
    public void idDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getId()).isEmpty();
    }

    @Test
    public void hasAThumbnail() {
        final String thumbnail = "mylink";

        final TopicData topicData = new TopicData.Builder().thumbnail(thumbnail).build();

        assertThat(topicData.getThumbnail()).isEqualTo(thumbnail);
    }

    @Test
    public void thumbnailDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getThumbnail()).isEmpty();
    }

    @Test
    public void hasAName() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().name(realTopic.getName(FeelhubLanguage.reference())).build();

        assertThat(topicData.getName()).isEqualTo(realTopic.getName(FeelhubLanguage.reference()));
    }

    @Test
    public void nameValueDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getName()).isEmpty();
    }

    @Test
    public void hasAType() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().type(realTopic.getType()).build();

        assertThat(topicData.getType()).isEqualTo(realTopic.getType().toString());
    }

    @Test
    public void hasTopicTypeDefault() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getType()).isEqualTo(UnusableTopicTypes.None.toString());
    }

    @Test
    public void hasSubtypes() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().subtypes(realTopic.getSubTypes()).build();

        assertThat(topicData.getSubTypes()).isEqualTo(realTopic.getSubTypes());
    }

    @Test
    public void subTypesHasADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getSubTypes()).isEmpty();
    }

    @Test
    public void topicHasUris() {
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();

        final TopicData topicData = new TopicData.Builder().uris(httpTopic.getUris()).build();

        final List<String> uris = Lists.newArrayList();
        for (final Uri uri : httpTopic.getUris()) {
            uris.add(uri.toString());
        }
        assertThat(topicData.getUris()).isEqualTo(uris);
    }

    @Test
    public void urlsHasADefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getUris()).isEmpty();
    }

    @Test
    public void hasADescription() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().description(realTopic.getDescription(FeelhubLanguage.reference())).build();

        assertThat(topicData.getDescription()).isEqualTo(realTopic.getDescription(FeelhubLanguage.reference()));
    }

    @Test
    public void descriptionValueDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getName()).isEmpty();
    }

    @Test
    public void hasAnHapptFeelingCount() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.increasesFeelingCount(TestFactories.feelings().happyFeeling());

        final TopicData topicData = new TopicData.Builder().happyFeelingCount(realTopic.getHappyFeelingCount()).build();

        assertThat(topicData.getHappyFeelingCount()).isEqualTo(realTopic.getHappyFeelingCount());
    }

    @Test
    public void happyFeelingCountHasDefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getHappyFeelingCount()).isEqualTo(0);
    }

    @Test
    public void hasABoredFeelingCount() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.increasesFeelingCount(TestFactories.feelings().boredFeeling());

        final TopicData topicData = new TopicData.Builder().boredFeelingCount(realTopic.getBoredFeelingCount()).build();

        assertThat(topicData.getBoredFeelingCount()).isEqualTo(realTopic.getBoredFeelingCount());
    }

    @Test
    public void boredFeelingCountHasDefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getBoredFeelingCount()).isEqualTo(0);
    }

    @Test
    public void hasASadFeelingCount() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.increasesFeelingCount(TestFactories.feelings().sadFeeling());

        final TopicData topicData = new TopicData.Builder().sadFeelingCount(realTopic.getSadFeelingCount()).build();

        assertThat(topicData.getSadFeelingCount()).isEqualTo(realTopic.getSadFeelingCount());
    }

    @Test
    public void sadFeelingCountHasDefaultValue() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getSadFeelingCount()).isEqualTo(0);
    }

    @Test
    public void hasFeelings() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        realTopic.increasesFeelingCount(TestFactories.feelings().sadFeeling());

        final TopicData topicData = new TopicData.Builder().hasFeelings(realTopic.getHasFeelings()).build();

        assertThat(topicData.getHasFeelings()).isEqualTo(realTopic.getHasFeelings());
    }

    @Test
    public void hasCreationDate() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().creationDate(realTopic.getCreationDate()).build();

        assertThat(topicData.getCreationDate()).isEqualTo(realTopic.getCreationDate().getMillis());
    }

    @Test
    public void hasLastModificationDate() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();

        final TopicData topicData = new TopicData.Builder().lastModificatioNDate(realTopic.getLastModificationDate()).build();

        assertThat(topicData.getLastModificationDate()).isEqualTo(realTopic.getLastModificationDate().getMillis());
    }
}
