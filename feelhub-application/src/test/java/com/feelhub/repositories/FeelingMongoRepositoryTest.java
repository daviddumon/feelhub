package com.feelhub.repositories;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.List;

import static org.fest.assertions.Assertions.*;

public class FeelingMongoRepositoryTest extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        topic = TestFactories.topics().newCompleteRealTopic();
        user = TestFactories.users().createFakeActiveUser("userforrepo@mail.com");
    }

    @Test
    public void canPersistAFeeling() {
        final Feeling feeling = createAFeeling();

        Repositories.feelings().add(feeling);

        final DBCollection feelings = getMongo().getCollection("feeling");
        final DBObject query = new BasicDBObject();
        query.put("_id", feeling.getId());
        final DBObject feelingFound = feelings.findOne(query);
        assertThat(feelingFound).isNotNull();
        assertThat(feelingFound.get("_id")).isEqualTo(feeling.getId());
        assertThat(feelingFound.get("text").toString()).isEqualTo(feeling.getText());
        assertThat(feelingFound.get("languageCode").toString()).isEqualTo(feeling.getLanguageCode());
        assertThat(feelingFound.get("creationDate")).isEqualTo(feeling.getCreationDate().getMillis());
        assertThat(feelingFound.get("lastModificationDate")).isEqualTo(feeling.getCreationDate().getMillis());
        assertThat(feelingFound.get("userId")).isEqualTo(user.getId());
        assertThat(feelingFound.get("topicId")).isEqualTo(topic.getId());
        assertThat(feelingFound.get("feelingValue")).isEqualTo(feeling.getFeelingValue().toString());
        assertThat(feelingFound.get("force")).isEqualTo(feeling.getForce());
    }

    @Test
    public void canGetAFeeling() {
        final Feeling feeling = TestFactories.feelings().newFeeling();

        final Feeling feelingFound = Repositories.feelings().get(feeling.getId());

        assertThat(feelingFound).isEqualTo(feeling);
    }

    @Test
    public void canGetAllFeelings() {
        final Feeling feeling0 = TestFactories.feelings().newFeeling();
        final Feeling feeling1 = TestFactories.feelings().newFeeling();

        final List<Feeling> feelings = Repositories.feelings().getAll();

        assertThat(feelings.size()).isEqualTo(2);
        assertThat(feelings.get(0)).isEqualTo(feeling0);
        assertThat(feelings.get(1)).isEqualTo(feeling1);
    }

    @Test
    public void canGetAllFeelingsForTopic() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final Feeling feeling1 = TestFactories.feelings().newFeeling(realTopic1, "comment 1");
        final Feeling feeling2 = TestFactories.feelings().newFeeling(realTopic1, "comment 2");
        final Feeling feeling3 = TestFactories.feelings().newFeeling(realTopic2, "comment 3");

        final List<Feeling> feelings = Repositories.feelings().forTopicId(realTopic1.getId());

        assertThat(feelings.size()).isEqualTo(2);
    }

    @Test
    public void canGetAllFeelingsForATopicAnUserAndAFeelingValue() {
        final RealTopic realTopicA = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopicB = TestFactories.topics().newCompleteRealTopic();
        final User userA = TestFactories.users().createFakeActiveUser("mail@mail.com");
        final User userB = TestFactories.users().createFakeActiveUser("mail2@mail.com");
        final Feeling feeling1 = TestFactories.feelings().feelingWithAnUserATopicAndAFeelingValue(userA, realTopicA, FeelingValue.bad);
        final Feeling feeling2 = TestFactories.feelings().feelingWithAnUserATopicAndAFeelingValue(userA, realTopicA, FeelingValue.good);
        final Feeling feeling3 = TestFactories.feelings().feelingWithAnUserATopicAndAFeelingValue(userB, realTopicA, FeelingValue.bad);
        final Feeling feeling4 = TestFactories.feelings().feelingWithAnUserATopicAndAFeelingValue(userA, realTopicB, FeelingValue.bad);

        final List<Feeling> feelings = Repositories.feelings().forTopicIdUserIdAndFeelingValue(realTopicA.getId(), userA.getId(), FeelingValue.bad);

        assertThat(feelings.size()).isEqualTo(1);
    }

    private Feeling createAFeeling() {
        final Feeling feeling = new Feeling(user.getId(), topic.getId());
        feeling.setText("This is a comment");
        feeling.setFeelingValue(FeelingValue.good);
        feeling.setLanguageCode("en");
        return feeling;
    }

    private RealTopic topic;
    private User user;
}
