package com.feelhub.domain.feeling;

import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.*;
import org.junit.*;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class FeelingTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        user = TestFactories.users().createFakeActiveUser("mail@mail.com");
        realTopic = TestFactories.topics().newCompleteRealTopic();
    }

    @Test
    public void canCreateAFeelingWithAUserAndATopic() {
        final Feeling feeling = createAFeeling();

        assertThat(feeling.getId()).isNotNull();
        assertThat(feeling.getId().getClass()).isEqualTo(UUID.class);
        assertThat(feeling.getUserId()).isEqualTo(user.getId());
        assertThat(feeling.getTopicId()).isEqualTo(realTopic.getId());
        assertThat(feeling.getCreationDate()).isNotNull();
        assertThat(feeling.getCreationDate()).isEqualTo(time.getNow());
        assertThat(feeling.getLastModificationDate()).isEqualTo(time.getNow());
    }

    @Test
    public void hasAFeelingValueOfNeutralOnCreation() {
        final Feeling feeling = createAFeeling();

        assertThat(feeling.getFeelingValue()).isEqualTo(FeelingValue.bored);
    }

    @Test
    public void canSetAFeelingValue() {
        final Feeling feeling = createAFeeling();

        feeling.setFeelingValue(FeelingValue.happy);

        assertThat(feeling.getFeelingValue()).isEqualTo(FeelingValue.happy);
    }

    @Test
    public void hasALanguage() {
        final Feeling feeling = createAFeeling();

        feeling.setLanguageCode("en");

        assertThat(feeling.getLanguageCode()).isEqualTo("en");
    }

    @Test
    public void canSetAnOptionalText() {
        final Feeling feeling = createAFeeling();
        final String comment = "this is a comment";

        feeling.setText(comment);

        assertThat(feeling.getText()).isEqualTo(comment);
    }

    @Test
    public void canAddARelatedTopic() {
        final Feeling feeling = createAFeeling();
        final HttpTopic httpTopic = TestFactories.topics().newCompleteHttpTopic();

        feeling.addRelatedTopic(httpTopic.getCurrentId());

        assertThat(feeling.getRelatedTopics().size()).isEqualTo(1);
    }

    @Test
    public void aFeelingHasAForce() {
        final Feeling feeling = createAFeeling();

        assertThat(feeling.getForce()).isEqualTo(1);
    }

    @Test
    public void canSetForce() {
        final Feeling feeling = createAFeeling();

        feeling.setForce(2);

        assertThat(feeling.getForce()).isEqualTo(2);
    }

    private Feeling createAFeeling() {
        return new Feeling(user.getId(), realTopic.getId());
    }

    private User user;
    private RealTopic realTopic;
}
