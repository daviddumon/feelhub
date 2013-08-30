package com.feelhub.web.dto;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.joda.time.DateTime;
import org.junit.*;
import org.ocpsoft.prettytime.PrettyTime;

import static org.fest.assertions.Assertions.*;

public class FeelingDataTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WithDomainEvent withDomainEvent = new WithDomainEvent();

    @Rule
    public SystemTime time = SystemTime.fixed();

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
    public void hasATopicId() {
        final RealTopic topic = TestFactories.topics().newCompleteRealTopic();
        final FeelingData feelingData = new FeelingData.Builder().topicId(topic.getCurrentId()).build();

        assertThat(feelingData.getTopicId()).isEqualTo(topic.getCurrentId());
    }

    @Test
    public void hasAFeelingValue() {
        final FeelingData feelingData = new FeelingData.Builder().feelingValue(FeelingValue.good).build();

        assertThat(feelingData.getFeelingValue()).isEqualTo(FeelingValue.good);
    }

    @Test
    public void hasCreationDate() {
        final FeelingData feelingData = new FeelingData.Builder().creationDate(time.getNow()).build();

        assertThat(feelingData.getCreationDate()).isNotNull();
    }

    @Test
    public void feelingValueDefaultToNull() {
        final FeelingData feelingData = new FeelingData.Builder().build();

        assertThat(feelingData.getFeelingValue()).isNull();
    }

    @Test
    public void canGetCorrectDate() {
        final DateTime creationDate = time.getNow();
        final FeelingData feelingData = new FeelingData.Builder().creationDate(creationDate).build();

        assertThat(feelingData.getCreationDate()).isEqualTo(new PrettyTime().format(creationDate.toDate()));
    }
}
