package com.feelhub.repositories;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.domain.user.User;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;

public class TestsFeelingMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPersist() {
        final RealTopic realTopic = TestFactories.topics().newCompleteRealTopic();
        final User activeUser = TestFactories.users().createFakeActiveUser("userforrepo@mail.com");
        final Feeling feeling = new Feeling(UUID.randomUUID(), "yeah", activeUser.getId());
        final Sentiment sentiment = TestFactories.sentiments().newSentiment(realTopic, SentimentValue.bad);
        feeling.addSentiment(sentiment);
        feeling.setLanguageCode("en");

        Repositories.feelings().add(feeling);

        final DBCollection feelings = mongo.getCollection("feeling");
        final DBObject query = new BasicDBObject();
        query.put("_id", feeling.getId());
        final DBObject feelingFound = feelings.findOne(query);
        assertThat(feelingFound).isNotNull();
        assertThat(feelingFound.get("_id")).isEqualTo(feeling.getId());
        assertThat(feelingFound.get("text").toString()).isEqualTo(feeling.getText());
        assertThat(feelingFound.get("rawText").toString()).isEqualTo(feeling.getText());
        assertThat(feelingFound.get("languageCode").toString()).isEqualTo(feeling.getLanguageCode());
        assertThat(feelingFound.get("creationDate")).isEqualTo(feeling.getCreationDate().getMillis());
        assertThat(feelingFound.get("lastModificationDate")).isEqualTo(feeling.getCreationDate().getMillis());
        assertThat(feelingFound.get("userId")).isEqualTo(activeUser.getId());
    }

    @Test
    public void canGet() {
        final Feeling feeling = TestFactories.feelings().newFeeling();

        final Feeling feelingFound = Repositories.feelings().get(feeling.getId());

        assertThat(feelingFound).isEqualTo(feeling);
    }

    @Test
    public void canGetAll() {
        final Feeling feeling0 = TestFactories.feelings().newFeeling();
        final Feeling feeling1 = TestFactories.feelings().newFeeling();

        final List<Feeling> feelings = Repositories.feelings().getAll();

        assertThat(feelings.size()).isEqualTo(2);
        assertThat(feelings.get(0)).isEqualTo(feeling0);
        assertThat(feelings.get(1)).isEqualTo(feeling1);
    }

    @Test
    public void canPersistMapOfSentiments() {
        final Feeling feeling = TestFactories.feelings().newFeeling();

        Repositories.feelings().add(feeling);

        final DBCollection feelings = mongo.getCollection("feeling");
        final DBObject query = new BasicDBObject();
        query.put("_id", feeling.getId());
        final DBObject feelingFound = feelings.findOne(query);
        assertThat(feelingFound.get("sentiments")).isNotNull();
    }

    @Ignore("on doit faire evoluer mongolink pour faire ce test ...")
    @Test
    public void canGetAllFeelingsForTopic() {
        final RealTopic realTopic1 = TestFactories.topics().newCompleteRealTopic();
        final RealTopic realTopic2 = TestFactories.topics().newCompleteRealTopic();
        final Feeling op1 = TestFactories.feelings().newFeeling();
        final Feeling op2 = TestFactories.feelings().newFeeling();
        final Feeling op3 = TestFactories.feelings().newFeeling();
        op1.addSentiment(new Sentiment(realTopic1.getId(), SentimentValue.good));
        op1.addSentiment(new Sentiment(realTopic2.getId(), SentimentValue.bad));
        op2.addSentiment(new Sentiment(realTopic1.getId(), SentimentValue.good));
        op3.addSentiment(new Sentiment(realTopic2.getId(), SentimentValue.good));

        final List<Feeling> feelings = Repositories.feelings().forTopicId(realTopic1.getId());

        assertThat(feelings.size()).isEqualTo(2);
    }
}
