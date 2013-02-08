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
        List<SentimentData> sentimentDataList = Lists.newArrayList();
        sentimentDataList.add(new SentimentData.Builder().id(UUID.randomUUID()).build());
        sentimentDataList.add(new SentimentData.Builder().id(UUID.randomUUID()).build());
        sentimentDataList.add(new SentimentData.Builder().id(UUID.randomUUID()).build());

        final FeelingData feelingData = new FeelingData.Builder().sentimentDatas(sentimentDataList).build();

        assertThat(feelingData.getSentimentDatas()).isEqualTo(sentimentDataList);
    }

    @Test
    public void canSetTopicDataAndContext() {
        List<SentimentData> sentimentDataList = Lists.newArrayList();
        final UUID contextId = UUID.randomUUID();
        sentimentDataList.add(new SentimentData.Builder().id(contextId).sentimentValue(SentimentValue.good).build());
        sentimentDataList.add(new SentimentData.Builder().id(UUID.randomUUID()).build());
        sentimentDataList.add(new SentimentData.Builder().id(UUID.randomUUID()).build());

        final FeelingData feelingData = new FeelingData.Builder().sentimentDatas(sentimentDataList, contextId).build();

        assertThat(feelingData.getSentimentDatas().size()).isEqualTo(sentimentDataList.size() - 1);
        assertThat(feelingData.getFeelingSentimentValue()).isEqualTo(SentimentValue.good);
    }

    @Test
    public void feelingSentimentValueDefaultToNull() {
        List<SentimentData> sentimentDataList = Lists.newArrayList();
        sentimentDataList.add(new SentimentData.Builder().id(UUID.randomUUID()).build());
        sentimentDataList.add(new SentimentData.Builder().id(UUID.randomUUID()).build());
        sentimentDataList.add(new SentimentData.Builder().id(UUID.randomUUID()).build());

        final FeelingData feelingData = new FeelingData.Builder().sentimentDatas(sentimentDataList).build();

        assertThat(feelingData.getFeelingSentimentValue()).isNull();
    }
}
