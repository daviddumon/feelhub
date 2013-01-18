package com.feelhub.web.dto;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.google.common.collect.Lists;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class TestsFeelingData {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void hasFeelingId() {
        final Feeling feeling = TestFactories.feelings().newFeeling();

        final FeelingData feelingData = new FeelingData.Builder().id(feeling.getId()).build();

        assertThat(feelingData.getId()).isEqualTo(feeling.getId());
    }

    @Test
    public void hasText() {
        final Feeling feeling = TestFactories.feelings().newFeeling();

        final FeelingData feelingData = new FeelingData.Builder().text(feeling.getText()).build();

        assertThat(feelingData.getText()).contains(feeling.getText());
    }

    @Test
    public void hasUserID() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");

        final FeelingData feelingData = new FeelingData.Builder().userId(user.getId()).build();

        assertThat(feelingData.getUserId()).isEqualTo(user.getId());
    }

    @Test
    public void hasLanguageCode() {
        final FeelingData feelingData = new FeelingData.Builder().languageCode(FeelhubLanguage.reference().getCode()).build();

        assertThat(feelingData.getLanguageCode()).isEqualTo(FeelhubLanguage.reference().getCode());
    }

    @Test
    public void hasTopicDataList() {
        List<TopicData> topicDataList = Lists.newArrayList();
        topicDataList.add(new TopicData.Builder().id(UUID.randomUUID()).build());
        topicDataList.add(new TopicData.Builder().id(UUID.randomUUID()).build());
        topicDataList.add(new TopicData.Builder().id(UUID.randomUUID()).build());

        final FeelingData feelingData = new FeelingData.Builder().topicDatas(topicDataList).build();

        assertThat(feelingData.getTopicDatas()).isEqualTo(topicDataList);
    }

    @Test
    public void canSetTopicDataAndContext() {
        List<TopicData> topicDataList = Lists.newArrayList();
        final UUID contextId = UUID.randomUUID();
        topicDataList.add(new TopicData.Builder().id(contextId).sentimentValue(SentimentValue.good).build());
        topicDataList.add(new TopicData.Builder().id(UUID.randomUUID()).build());
        topicDataList.add(new TopicData.Builder().id(UUID.randomUUID()).build());

        final FeelingData feelingData = new FeelingData.Builder().topicDatas(topicDataList, contextId).build();

        assertThat(feelingData.getTopicDatas().size()).isEqualTo(topicDataList.size() - 1);
        assertThat(feelingData.getFeelingSentimentValue()).isEqualTo(SentimentValue.good);
    }

    @Test
    public void feelingSentimentValueDefaultToNone() {
        List<TopicData> topicDataList = Lists.newArrayList();
        topicDataList.add(new TopicData.Builder().id(UUID.randomUUID()).build());
        topicDataList.add(new TopicData.Builder().id(UUID.randomUUID()).build());
        topicDataList.add(new TopicData.Builder().id(UUID.randomUUID()).build());

        final FeelingData feelingData = new FeelingData.Builder().topicDatas(topicDataList).build();

        assertThat(feelingData.getFeelingSentimentValue()).isEqualTo(SentimentValue.none);
    }
}
