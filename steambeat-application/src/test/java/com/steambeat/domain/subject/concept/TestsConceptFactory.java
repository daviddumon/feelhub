package com.steambeat.domain.subject.concept;

import com.google.common.collect.Lists;
import com.steambeat.domain.analytics.alchemy.readmodel.*;
import com.steambeat.domain.analytics.alchemy.thesaurus.*;
import com.steambeat.domain.analytics.identifiers.uri.Uri;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class TestsConceptFactory {

    @Test
    public void canCreateFromNamedEntity() {
        final ConceptFactory conceptFactory = new ConceptFactory();
        final AlchemyJsonEntity entity = getEntity();

        final Concept concept = conceptFactory.newConcept(entity);

        assertThat(concept.getLanguage(), is(Language.forString("english")));
        assertThat(concept.getType(), is(Type.forString("Method")));
        assertThat(concept.getRelevance(), is(entity.relevance));
        assertThat(concept.getCount(), is(entity.count));
        assertThat(concept.getText(), is("Agile"));
        final AlchemyJsonDisambiguated disambiguated = entity.disambiguated;
        assertThat(concept.getName(), is(disambiguated.name));
        assertThat(concept.getSubTypes().size(), is(1));
        assertThat(concept.getSubTypes().get(0), is("subtype"));
        assertThat(concept.getWebsite(), is(new Uri(disambiguated.website)));
        assertThat(concept.getGeo(), is(disambiguated.geo));
        assertThat(concept.getDbpedia(), is(new Uri(disambiguated.dbpedia)));
        assertThat(concept.getYago(), is(new Uri(disambiguated.yago)));
        assertThat(concept.getOpencyc(), is(new Uri(disambiguated.opencyc)));
        assertThat(concept.getUmbel(), is(new Uri(disambiguated.umbel)));
        assertThat(concept.getFreebase(), is(new Uri(disambiguated.freebase)));
        assertThat(concept.getCiaFactbook(), is(new Uri(disambiguated.ciaFactbook)));
        assertThat(concept.getCensus(), is(Uri.empty()));
        assertThat(concept.getGeonames(), is(new Uri(disambiguated.geonames)));
        assertThat(concept.getMusicBrainz(), is(new Uri(disambiguated.musicBrainz)));
        assertThat(concept.getCrunchbase(), is(new Uri(disambiguated.crunchbase)));
        assertThat(concept.getSemanticCrunchbase(), is(new Uri(disambiguated.semanticCrunchbase)));
    }

    private AlchemyJsonEntity getEntity() {
        final AlchemyJsonEntity alchemyJsonEntity = new AlchemyJsonEntity();
        alchemyJsonEntity.language = "english";
        alchemyJsonEntity.type = "Method";
        alchemyJsonEntity.relevance = 0.3;
        alchemyJsonEntity.count = 2;
        alchemyJsonEntity.text = "Agile";
        alchemyJsonEntity.disambiguated = new AlchemyJsonDisambiguated();
        alchemyJsonEntity.disambiguated.name = "tdd";
        alchemyJsonEntity.disambiguated.subType = Lists.newArrayList();
        alchemyJsonEntity.disambiguated.subType.add("subtype");
        alchemyJsonEntity.disambiguated.website = "http://fakewebsite";
        alchemyJsonEntity.disambiguated.geo = "1 -2";
        alchemyJsonEntity.disambiguated.dbpedia = "dbpedia";
        alchemyJsonEntity.disambiguated.yago = "yago";
        alchemyJsonEntity.disambiguated.opencyc = "opencyc";
        alchemyJsonEntity.disambiguated.umbel = "umbel";
        alchemyJsonEntity.disambiguated.freebase = "freebase";
        alchemyJsonEntity.disambiguated.ciaFactbook = "ciaFactbook";
        alchemyJsonEntity.disambiguated.census = "";
        alchemyJsonEntity.disambiguated.geonames = "geonames";
        alchemyJsonEntity.disambiguated.musicBrainz = "musicBrainz";
        alchemyJsonEntity.disambiguated.crunchbase = "crunchbase";
        alchemyJsonEntity.disambiguated.semanticCrunchbase = "semanticCrunchbase";
        return alchemyJsonEntity;
    }
}
