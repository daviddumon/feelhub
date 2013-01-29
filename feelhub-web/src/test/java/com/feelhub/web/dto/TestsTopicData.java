package com.feelhub.web.dto;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.UnusableTopicTypes;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.http.uri.Uri;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class TestsTopicData {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

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
    public void hasAnIllustration() {
        final String illustration = "mylink";

        final TopicData topicData = new TopicData.Builder().illustration(illustration).build();

        assertThat(topicData.getIllustration()).isEqualTo(illustration);
    }

    @Test
    public void illustrationLinkDefaultValueIsEmpty() {
        final TopicData topicData = new TopicData.Builder().build();

        assertThat(topicData.getIllustration()).isEmpty();
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

        List<String> uris = Lists.newArrayList();
        for (Uri uri : httpTopic.getUris()) {
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
    public void canRepresentInString() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final TopicData topicData = new TopicData.Builder().id(realTopic.getId()).type(realTopic.getType()).build();

        assertThat(topicData.toString()).isEqualTo("{\"id\":\"" + realTopic.getId() + "\",\"uris\":[],\"topicSentimentScore\":0,\"description\":\"\",\"illustration\":\"\",\"name\":\"\",\"subTypes\":[],\"type\":\"Automobile\"}");
    }

    @Test
    public void hasATopicSentimentScore() {
        final TopicData topicData = new TopicData.Builder().topicSentimentScore(10).build();

        assertThat(topicData.getTopicSentimentScore()).isEqualTo(10);
    }

}
