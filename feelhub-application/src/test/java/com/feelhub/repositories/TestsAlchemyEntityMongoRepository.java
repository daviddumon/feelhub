package com.feelhub.repositories;

import com.feelhub.domain.alchemy.*;
import com.feelhub.domain.topic.*;
import com.feelhub.test.*;
import com.google.common.collect.Lists;
import com.mongodb.*;
import org.junit.*;

import java.util.*;

import static org.fest.assertions.Assertions.*;


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
        alchemyEntity.setType(TopicType.Audio);
        alchemyEntity.setUmbel("umbel");
        alchemyEntity.setWebsite("website");
        alchemyEntity.setYago("yago");
        alchemyEntity.setRelevance(0.5);

        entityMongoRepository.add(alchemyEntity);

        final DBObject alchemyFound = getAlchemyEntityFromDB(alchemyEntity.getId());
        assertThat(alchemyFound).isNotNull();
        assertThat(alchemyFound.get("_id")).isEqualTo(alchemyEntity.getId());
        assertThat(alchemyFound.get("topicId")).isEqualTo(alchemyEntity.getTopicId());
        assertThat(alchemyFound.get("creationDate")).isEqualTo(alchemyEntity.getCreationDate().getMillis());
        assertThat(alchemyFound.get("lastModificationDate")).isEqualTo(alchemyEntity.getLastModificationDate().getMillis());
        assertThat(alchemyFound.get("census")).isEqualTo(alchemyEntity.getCensus());
        assertThat(alchemyFound.get("ciafactbook")).isEqualTo(alchemyEntity.getCiafactbook());
        assertThat(alchemyFound.get("crunchbase")).isEqualTo(alchemyEntity.getCrunchbase());
        assertThat(alchemyFound.get("dbpedia")).isEqualTo(alchemyEntity.getDbpedia());
        assertThat(alchemyFound.get("freebase")).isEqualTo(alchemyEntity.getFreebase());
        assertThat(alchemyFound.get("geo")).isEqualTo(alchemyEntity.getGeo());
        assertThat(alchemyFound.get("geonames")).isEqualTo(alchemyEntity.getGeonames());
        assertThat(alchemyFound.get("musicbrainz")).isEqualTo(alchemyEntity.getMusicbrainz());
        assertThat(alchemyFound.get("opencyc")).isEqualTo(alchemyEntity.getOpencyc());
        assertThat(alchemyFound.get("semanticcrunchbase")).isEqualTo(alchemyEntity.getSemanticcrunchbase());
        assertThat(alchemyFound.get("subtype")).isEqualTo(alchemyEntity.getSubtype());
        assertThat(TopicType.valueOf(alchemyFound.get("type").toString())).isEqualTo(alchemyEntity.getType());
        assertThat(alchemyFound.get("umbel")).isEqualTo(alchemyEntity.getUmbel());
        assertThat(alchemyFound.get("website")).isEqualTo(alchemyEntity.getWebsite());
        assertThat(alchemyFound.get("yago")).isEqualTo(alchemyEntity.getYago());
        assertThat(alchemyFound.get("relevance")).isEqualTo(0.5);
    }

    @Test
    public void canGetAlchemyEntity() {
        final DBCollection collection = mongo.getCollection("alchemyentity");
        final DBObject alchemy = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        alchemy.put("_id", id);
        collection.insert(alchemy);

        final AlchemyEntity alchemyEntityFound = entityMongoRepository.get(id);

        assertThat(alchemyEntityFound).isNotNull();
    }

    @Test
    public void canGetForATopic() {
        final Topic topic = TestFactories.topics().newTopic();
        TestFactories.alchemy().newAlchemyEntityEntity(topic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(topic.getId());
        TestFactories.alchemy().newAlchemyEntityEntity(topic.getId());

        final List<AlchemyEntity> alchemyEntities = entityMongoRepository.forTopicId(topic.getId());

        assertThat(alchemyEntities).isNotNull();
        assertThat(alchemyEntities.size()).isEqualTo(3);
    }

    private DBObject getAlchemyEntityFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("alchemyentity");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private AlchemyEntityRepository entityMongoRepository;
}
