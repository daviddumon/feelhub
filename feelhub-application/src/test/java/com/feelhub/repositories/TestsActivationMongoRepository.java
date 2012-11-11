package com.feelhub.repositories;

import com.feelhub.domain.user.Activation;
import com.feelhub.domain.user.ActivationRepository;
import com.feelhub.domain.user.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class TestsActivationMongoRepository extends TestWithMongoRepository {

    private ActivationRepository repository;

    @Before
    public void before() {
        repository = Repositories.activation();
    }

    @Test
    public void canPersist() {
        final Activation activation = new Activation(new User("id"));

        repository.add(activation);

        final DBCollection collection = mongo.getCollection("activation");
        final DBObject query = new BasicDBObject();
        query.put("_id", activation.getId());
        final DBObject activationFound = collection.findOne(query);
        assertThat(activationFound).isNotNull();
        assertThat(activationFound.get("_id")).isEqualTo(activation.getId());
        assertThat(activationFound.get("userId")).isEqualTo("id");

    }
}
