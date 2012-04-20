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
        final AlchemyXmlEntity entity = getEntity();

        final Concept concept = conceptFactory.newConcept(entity);

        assertThat(concept.getLanguage(), is(Language.forString("english")));
        assertThat(concept.getType(), is(Type.forString("Method")));
        assertThat(concept.getRelevance(), is(entity.relevance));
        assertThat(concept.getCount(), is(entity.count));
        assertThat(concept.getText(), is("Agile"));
        final AlchemyXmlDisambiguated disambiguated = entity.disambiguated;
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

    private AlchemyXmlEntity getEntity() {
        final AlchemyXmlEntity alchemyXmlEntity = new AlchemyXmlEntity();
        alchemyXmlEntity.language = "english";
        alchemyXmlEntity.type = "Method";
        alchemyXmlEntity.relevance = 0.3;
        alchemyXmlEntity.count = 2;
        alchemyXmlEntity.text = "Agile";
        alchemyXmlEntity.disambiguated = new AlchemyXmlDisambiguated();
        alchemyXmlEntity.disambiguated.name = "tdd";
        alchemyXmlEntity.disambiguated.subTypes = Lists.newArrayList();
        final AlchemyXmlSubtype alchemyXmlSubtype = new AlchemyXmlSubtype();
        alchemyXmlSubtype.text = "subtype";
        alchemyXmlEntity.disambiguated.subTypes.add(alchemyXmlSubtype);
        alchemyXmlEntity.disambiguated.website = "http://fakewebsite";
        alchemyXmlEntity.disambiguated.geo = "1 -2";
        alchemyXmlEntity.disambiguated.dbpedia = "dbpedia";
        alchemyXmlEntity.disambiguated.yago = "yago";
        alchemyXmlEntity.disambiguated.opencyc = "opencyc";
        alchemyXmlEntity.disambiguated.umbel = "umbel";
        alchemyXmlEntity.disambiguated.freebase = "freebase";
        alchemyXmlEntity.disambiguated.ciaFactbook = "ciaFactbook";
        alchemyXmlEntity.disambiguated.census = "";
        alchemyXmlEntity.disambiguated.geonames = "geonames";
        alchemyXmlEntity.disambiguated.musicBrainz = "musicBrainz";
        alchemyXmlEntity.disambiguated.crunchbase = "crunchbase";
        alchemyXmlEntity.disambiguated.semanticCrunchbase = "semanticCrunchbase";
        return alchemyXmlEntity;
    }
}
