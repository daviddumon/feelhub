package com.steambeat.domain.analytics.alchemy;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.analytics.*;
import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.*;
import org.mongolink.domain.criteria.*;

import java.util.*;

public class AlchemyEntityAnalyzer {

    @Inject
    public AlchemyEntityAnalyzer(final AlchemyEntityProvider alchemyEntityProvider, final SessionProvider sessionProvider) {
        this.provider = alchemyEntityProvider;
        this.sessionProvider = sessionProvider;
    }

    public void analyze(final WebPage webpage) {
        final List<AlchemyJsonEntity> results = provider.entitiesFor(webpage);
        createConcepts(results);
        createRelations(webpage);
    }

    private void createConcepts(final List<AlchemyJsonEntity> results) {
        for (final AlchemyJsonEntity alchemyJsonEntity : results) {
            concepts.add(createConcept(alchemyJsonEntity));
        }
    }

    private Concept createConcept(final AlchemyJsonEntity alchemyJsonEntity) {
        final UUID id = findExistingConcept(alchemyJsonEntity);
        if (id == null) {
            final Concept concept = new ConceptFactory().newConcept(alchemyJsonEntity);
            Repositories.subjects().add(concept);
            return concept;
        } else {
            return new ConceptFactory().lookUpConcept(id);
        }
    }

    private UUID findExistingConcept(final AlchemyJsonEntity alchemyJsonEntity) {
        final Criteria criteria = sessionProvider.get().createCriteria(Concept.class);
        criteria.add(Restrictions.equals("text", getGoodText(alchemyJsonEntity)));
        final List<Concept> concepts = criteria.list();
        if (concepts.isEmpty()) {
            return null;
        } else {
            return concepts.get(0).getId();
        }
    }

    private String getGoodText(final AlchemyJsonEntity entity) {
        if (entity.disambiguated.name.isEmpty()) {
            return entity.text;
        }
        return entity.disambiguated.name;
    }

    private void createRelations(final WebPage webpage) {
        for (Concept concept : concepts) {
            link(webpage, concept);
            for (int i = concepts.lastIndexOf(concept); i < concepts.size(); i++) {
                Concept otherConcept = concepts.get(i);
                if (!concept.equals(otherConcept)) {
                    link(concept, otherConcept);
                }
            }
        }
    }

    private void link(final Subject left, final Subject right) {
        final Relation relation1 = getRelationFactory().newRelation(left, right);
        final Relation relation2 = getRelationFactory().newRelation(right, left);
        Repositories.relations().add(relation1);
        Repositories.relations().add(relation2);
    }

    private RelationFactory getRelationFactory() {
        return new RelationFactory();
    }

    private final AlchemyEntityProvider provider;
    private SessionProvider sessionProvider;
    private List<Concept> concepts = Lists.newArrayList();
}
