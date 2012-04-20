package com.steambeat.domain.analytics.alchemy;

import com.google.inject.Inject;
import com.steambeat.domain.analytics.*;
import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyXmlEntity;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.List;

public class NamedEntityAnalyzer {

    @Inject
    public NamedEntityAnalyzer(final NamedEntityProvider provider) {
        this.provider = provider;
    }

    public void analyze(final WebPage webpage) {
        final List<AlchemyXmlEntity> results = provider.entitiesFor(webpage);
        for (final AlchemyXmlEntity alchemyXmlEntity : results) {
            link(webpage, createConcept(alchemyXmlEntity));
        }
    }

    private Concept createConcept(final AlchemyXmlEntity alchemyXmlEntity) {
        final Concept concept = new ConceptFactory().newConcept(alchemyXmlEntity);
        Repositories.subjects().add(concept);
        return concept;
    }

    private void link(final WebPage webpage, final Concept concept) {
        final Relation relation1 = getRelationFactory().newRelation(webpage, concept);
        final Relation relation2 = getRelationFactory().newRelation(concept, webpage);
        Repositories.relations().add(relation1);
        Repositories.relations().add(relation2);
    }

    private RelationFactory getRelationFactory() {
        return new RelationFactory();
    }

    private final NamedEntityProvider provider;
}
