package com.feelhub.repositories;

import com.feelhub.domain.feeling.*;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.user.User;
import com.feelhub.test.*;
import com.mongodb.*;
import org.junit.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsFeelingMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Test
    public void canPersist() {
        final Reference reference = TestFactories.references().newReference();
        final User activeUser = TestFactories.users().createFakeActiveUser("userforrepo@mail.com");
        final Feeling feeling = new Feeling("yeah", activeUser.getId());
        feeling.addSentiment(reference, SentimentValue.bad);
        feeling.setLanguageCode("en");

        Repositories.feelings().add(feeling);

        final DBCollection feelings = mongo.getCollection("feeling");
        final DBObject query = new BasicDBObject();
        query.put("_id", feeling.getId());
        final DBObject feelingFound = feelings.findOne(query);
        assertThat(feelingFound, notNullValue());
        assertThat(feelingFound.get("_id"), is((Object) feeling.getId()));
        assertThat(feelingFound.get("text").toString(), is(feeling.getText()));
        assertThat(feelingFound.get("languageCode").toString(), is(feeling.getLanguageCode()));
        assertThat(feelingFound.get("creationDate"), is((Object) feeling.getCreationDate().getMillis()));
        assertThat(feelingFound.get("lastModificationDate"), is((Object) feeling.getCreationDate().getMillis()));
        assertThat(feelingFound.get("userId").toString(), is(activeUser.getId()));
    }

    @Test
    public void canGet() {
        final Feeling feeling = TestFactories.feelings().newFeeling();

        final Feeling feelingFound = Repositories.feelings().get(feeling.getId());

        assertThat(feelingFound, is(feeling));
    }

    @Test
    public void canGetAll() {
        final Feeling feeling0 = TestFactories.feelings().newFeeling();
        final Feeling feeling1 = TestFactories.feelings().newFeeling();

        final List<Feeling> feelings = Repositories.feelings().getAll();

        assertThat(feelings.size(), is(2));
        assertThat(feelings.get(0), is(feeling0));
        assertThat(feelings.get(1), is(feeling1));
    }

    @Test
    public void canPersistMapOfSentiments() {
        final Feeling feeling = TestFactories.feelings().newFeeling();

        Repositories.feelings().add(feeling);

        final DBCollection feelings = mongo.getCollection("feeling");
        final DBObject query = new BasicDBObject();
        query.put("_id", feeling.getId());
        final DBObject feelingFound = feelings.findOne(query);
        assertThat(feelingFound.get("sentiments"), notNullValue());
    }

    @Ignore("on doit faire evoluer mongolink pour faire ce test ...")
    @Test
    public void canGetAllFeelingsForReference() {
        final Reference ref1 = TestFactories.references().newReference();
        final Reference ref2 = TestFactories.references().newReference();
        final Feeling op1 = TestFactories.feelings().newFeeling();
        final Feeling op2 = TestFactories.feelings().newFeeling();
        final Feeling op3 = TestFactories.feelings().newFeeling();
        op1.addSentiment(ref1, SentimentValue.good);
        op1.addSentiment(ref2, SentimentValue.bad);
        op2.addSentiment(ref1, SentimentValue.good);
        op3.addSentiment(ref2, SentimentValue.good);

        final List<Feeling> feelings = Repositories.feelings().forReferenceId(ref1.getId());

        assertThat(feelings.size(), is(2));
    }
}
