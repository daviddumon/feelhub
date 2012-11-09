package com.feelhub.repositories;

import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.topic.Topic;
import com.feelhub.test.*;
import com.google.common.collect.Lists;
import com.mongodb.*;
import org.junit.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAlchemyEntityMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        entityMongoRepository = Repositories.alchemyEntities();
    }

    @Test
    public void canPersistAlchemyEntity() {
        final AlchemyEntity alchemyEntity = new AlchemyEntity(TestFactories.topics().newTopic().getId());
        alchemyEntity.setCensus("census");
        alchemyEntity.setCiafactbook("ciafactbook");
        alchemyEntity.setCrunchbase("crunchbase");
        alchemyEntity.setDbpedia("dbpedia");
        alchemyEntity.setFreebase("freebase");
        alchemyEntity.setGeo("geo");
        alchemyEntity.setGeonames("geonames");
        alchemyEntity.setMusicbrainz("musicbrainz");
        alchemyEntity.setOpencyc("opencyc");
        alchemyEntity.setSemanticcrunchbase("crunchbase");
        final List<String> subTypes = Lists.newArrayList();
        subTypes.add("sub1");
        subTypes.add("sub2");
        alchemyEntity.setSubtype(subTypes);
        alchemyEntity.setType("type");
        alchemyEntity.setUmbel("umbel");
        alchemyEntity.setWebsite("website");
        alchemyEntity.setYago("yago");
        alchemyEntity.setRelevance(0.5);

        entityMongoRepository.add(alchemyEntity);

        final DBObject alchemyFound = getAlchemyEntityFromDB(alchemyEntity.getId());
        assertThat(alchemyFound, notNullValue());
        assertThat(alchemyFound.get("_id"), is(alchemyEntity.getId()));
        assertThat(alchemyFound.get("topicId"), is((Object) alchemyEntity.getTopicId()));
        assertThat(alchemyFound.get("creationDate"), is((Object) alchemyEntity.getCreationDate().getMillis()));
        assertThat(alchemyFound.get("lastModificationDate"), is((Object) alchemyEntity.getLastModificationDate().getMillis()));
        assertThat(alchemyFound.get("census"), is((Object) alchemyEntity.getCensus()));
        assertThat(alchemyFound.get("ciafactbook"), is((Object) alchemyEntity.getCiafactbook()));
        assertThat(alchemyFound.get("crunchbase"), is((Object) alchemyEntity.getCrunchbase()));
        assertThat(alchemyFound.get("dbpedia"), is((Object) alchemyEntity.getDbpedia()));
        assertThat(alchemyFound.get("freebase"), is((Object) alchemyEntity.getFreebase()));
        assertThat(alchemyFound.get("geo"), is((Object) alchemyEntity.getGeo()));
        assertThat(alchemyFound.get("geonames"), is((Object) alchemyEntity.getGeonames()));
        assertThat(alchemyFound.get("musicbrainz"), is((Object) alchemyEntity.getMusicbrainz()));
        assertThat(alchemyFound.get("opencyc"), is((Object) alchemyEntity.getOpencyc()));
        assertThat(alchemyFound.get("semanticcrunchbase"), is((Object) alchemyEntity.getSemanticcrunchbase()));
        assertThat(alchemyFound.get("subtype"), is((Object) alchemyEntity.getSubtype()));
        assertThat(alchemyFound.get("type"), is((Object) alchemyEntity.getType()));
        assertThat(alchemyFound.get("umbel"), is((Object) alchemyEntity.getUmbel()));
        assertThat(alchemyFound.get("website"), is((Object) alchemyEntity.getWebsite()));
        assertThat(alchemyFound.get("yago"), is((Object) alchemyEntity.getYago()));
        assertThat(alchemyFound.get("relevance"), is((Object) 0.5));
    }

    @Test
    public void canGetAlchemyEntity() {
        final DBCollection collection = mongo.getCollection("alchemyentity");
        final DBObject alchemy = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        alchemy.put("_id", id);
        collection.insert(alchemy);

        final AlchemyEntity alchemyEntityFound = entityMongoRepository.get(id);

        assertThat(alchemyEntityFound, notNullValue());
    }

    @Test
    public void canGetForATopic() {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.alchemy().newAlchemyEntityEntity(topic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(topic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(topic.getId());

        final List<AlchemyEntity> alchemyEntities = entityMongoRepository.forTopicId(topic.getId());

        assertThat(alchemyEntities, notNullValue());
        assertThat(alchemyEntities.size(), is(3));
    }

    private DBObject getAlchemyEntityFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("alchemyentity");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private AlchemyEntityRepository entityMongoRepository;
}
