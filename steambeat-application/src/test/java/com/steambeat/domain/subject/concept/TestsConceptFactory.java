package com.steambeat.domain.subject.concept;

import com.google.common.collect.Lists;
import com.steambeat.domain.analytics.alchemy.NamedEntity;
import com.steambeat.domain.analytics.alchemy.thesaurus.*;
import com.steambeat.domain.analytics.identifiers.uri.*;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

public class TestsConceptFactory {

    @Test
    public void canCreateFromNamedEntity() {
        final ConceptFactory conceptFactory = new ConceptFactory();
        final NamedEntity namedEntity = getNamedEntity();

        final Concept concept = conceptFactory.newConcept(namedEntity);

        assertThat(concept.getLanguage(), is(Language.forString("english")));
        assertThat(concept.getType(), is(Type.forString("Method")));
        assertThat(concept.getRelevance(), is(namedEntity.relevance));
        assertThat(concept.getCount(), is(namedEntity.count));
        assertThat(concept.getText(), is("Agile"));
        assertThat(concept.getName(), is(namedEntity.name));
        assertThat(concept.getSubTypes(), is(namedEntity.subTypes));
        assertThat(concept.getWebsite(), is(new Uri(namedEntity.website)));
        assertThat(concept.getGeo(), is(namedEntity.geo));
        assertThat(concept.getDbpedia(), is(new Uri(namedEntity.dbpedia)));
        assertThat(concept.getYago(), is(new Uri(namedEntity.yago)));
        assertThat(concept.getOpencyc(), is(new Uri(namedEntity.opencyc)));
        assertThat(concept.getUmbel(), is(new Uri(namedEntity.umbel)));
        assertThat(concept.getFreebase(), is(new Uri(namedEntity.freebase)));
        assertThat(concept.getCiaFactbook(), is(new Uri(namedEntity.ciaFactbook)));
        assertThat(concept.getCensus(), is(Uri.empty()));
        assertThat(concept.getGeonames(), is(new Uri(namedEntity.geonames)));
        assertThat(concept.getMusicBrainz(), is(new Uri(namedEntity.musicBrainz)));
        assertThat(concept.getCrunchbase(), is(new Uri(namedEntity.crunchbase)));
        assertThat(concept.getSemanticCrunchbase(), is(new Uri(namedEntity.semanticCrunchbase)));
    }

    private NamedEntity getNamedEntity() {
        final NamedEntity namedEntity = new NamedEntity();
        namedEntity.language = "english";
        namedEntity.type = "Method";
        namedEntity.relevance = 0.3;
        namedEntity.count = 2;
        namedEntity.text = "Agile";
        namedEntity.name = "tdd";
        final ArrayList<String> subTypes = Lists.newArrayList();
        subTypes.add("subtype1");
        subTypes.add("subtype2");
        namedEntity.subTypes = subTypes;
        namedEntity.website = "http://fakewebsite";
        namedEntity.geo = "1 -2";
        namedEntity.dbpedia = "dbpedia";
        namedEntity.yago = "yago";
        namedEntity.opencyc = "opencyc";
        namedEntity.umbel = "umbel";
        namedEntity.freebase = "freebase";
        namedEntity.ciaFactbook = "ciaFactbook";
        namedEntity.census = "";
        namedEntity.geonames = "geonames";
        namedEntity.musicBrainz = "musicBrainz";
        namedEntity.crunchbase = "crunchbase";
        namedEntity.semanticCrunchbase = "semanticCrunchbase";
        return namedEntity;
    }
}
