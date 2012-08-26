package com.steambeat.repositories;

import com.google.common.collect.Lists;
import com.mongodb.*;
import com.steambeat.domain.alchemy.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.test.*;
import org.junit.*;

import java.util.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class TestsAlchemyMongoRepository extends TestWithMongoRepository {

    @Rule
    public SystemTime time = SystemTime.fixed();

    @Before
    public void before() {
        repository = Repositories.alchemys();
    }

    @Test
    public void canPersistAlchemy() {
        final Alchemy alchemy = new Alchemy(TestFactories.references().newReference().getId());
        alchemy.setCensus("census");
        alchemy.setCiafactbook("ciafactbook");
        alchemy.setCrunchbase("crunchbase");
        alchemy.setDbpedia("dbpedia");
        alchemy.setFreebase("freebase");
        alchemy.setGeo("geo");
        alchemy.setGeonames("geonames");
        alchemy.setMusicbrainz("musicbrainz");
        alchemy.setOpencyc("opencyc");
        alchemy.setSemanticcrunchbase("crunchbase");
        List<String> subTypes = Lists.newArrayList();
        subTypes.add("sub1");
        subTypes.add("sub2");
        alchemy.setSubtype(subTypes);
        alchemy.setType("type");
        alchemy.setUmbel("umbel");
        alchemy.setWebsite("website");
        alchemy.setYago("yago");
        alchemy.setRelevance(0.5);

        repository.add(alchemy);

        final DBObject alchemyFound = getAlchemyInformationFromDB(alchemy.getId());
        assertThat(alchemyFound, notNullValue());
        assertThat(alchemyFound.get("_id"), is(alchemy.getId()));
        assertThat(alchemyFound.get("referenceId"), is((Object) alchemy.getReferenceId()));
        assertThat(alchemyFound.get("creationDate"), is((Object) alchemy.getCreationDate().getMillis()));
        assertThat(alchemyFound.get("lastModificationDate"), is((Object) alchemy.getLastModificationDate().getMillis()));
        assertThat(alchemyFound.get("census"), is((Object) alchemy.getCensus()));
        assertThat(alchemyFound.get("ciafactbook"), is((Object) alchemy.getCiafactbook()));
        assertThat(alchemyFound.get("crunchbase"), is((Object) alchemy.getCrunchbase()));
        assertThat(alchemyFound.get("dbpedia"), is((Object) alchemy.getDbpedia()));
        assertThat(alchemyFound.get("freebase"), is((Object) alchemy.getFreebase()));
        assertThat(alchemyFound.get("geo"), is((Object) alchemy.getGeo()));
        assertThat(alchemyFound.get("geonames"), is((Object) alchemy.getGeonames()));
        assertThat(alchemyFound.get("musicbrainz"), is((Object) alchemy.getMusicbrainz()));
        assertThat(alchemyFound.get("opencyc"), is((Object) alchemy.getOpencyc()));
        assertThat(alchemyFound.get("semanticcrunchbase"), is((Object) alchemy.getSemanticcrunchbase()));
        assertThat(alchemyFound.get("subtype"), is((Object) alchemy.getSubtype()));
        assertThat(alchemyFound.get("type"), is((Object) alchemy.getType()));
        assertThat(alchemyFound.get("umbel"), is((Object) alchemy.getUmbel()));
        assertThat(alchemyFound.get("website"), is((Object) alchemy.getWebsite()));
        assertThat(alchemyFound.get("yago"), is((Object) alchemy.getYago()));
        assertThat(alchemyFound.get("relevance"), is((Object) 0.5));
    }

    @Test
    public void canGetAlchemy() {
        final DBCollection collection = mongo.getCollection("alchemy");
        final DBObject alchemy = new BasicDBObject();
        final UUID id = UUID.randomUUID();
        alchemy.put("_id", id);
        collection.insert(alchemy);

        final Alchemy alchemyFound = repository.get(id);

        assertThat(alchemyFound, notNullValue());
    }

    @Test
    public void canGetForAReference() {
        final Reference reference = TestFactories.references().newReference();
        TestFactories.alchemy().newAlchemy(reference.getId());
        TestFactories.alchemy().newAlchemy(reference.getId());
        TestFactories.alchemy().newAlchemy(reference.getId());

        final List<Alchemy> alchemys = repository.forReferenceId(reference.getId());

        assertThat(alchemys, notNullValue());
        assertThat(alchemys.size(), is(3));
    }

    private DBObject getAlchemyInformationFromDB(final Object id) {
        final DBCollection collection = mongo.getCollection("alchemy");
        final DBObject query = new BasicDBObject();
        query.put("_id", id);
        return collection.findOne(query);
    }

    private AlchemyRepository repository;
}
