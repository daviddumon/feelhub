package com.steambeat.domain.analytics.alchemy;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.analytics.*;
import com.steambeat.domain.analytics.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.analytics.identifiers.tag.Tag;
import com.steambeat.domain.subject.Subject;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.*;

public class AlchemyEntityAnalyzer {

    @Inject
    public AlchemyEntityAnalyzer(final AlchemyEntityProvider alchemyEntityProvider, final AssociationService associationService) {
        this.provider = alchemyEntityProvider;
        this.associationService = associationService;
    }

    public void analyze(final WebPage webpage) {
        final List<AlchemyJsonEntity> results = provider.entitiesFor(webpage);
        createAssociationsAndConcepts(results);
        createRelations(webpage);
    }

    private void createAssociationsAndConcepts(final List<AlchemyJsonEntity> results) {
        for (final AlchemyJsonEntity alchemyJsonEntity : results) {
            createAssociationsAndConceptsForEntity(alchemyJsonEntity);
        }
    }

    private void createAssociationsAndConceptsForEntity(final AlchemyJsonEntity alchemyJsonEntity) {
        final UUID id = findExistingConcept(alchemyJsonEntity);
        if (id == null) {
            final Concept concept = createConcept(alchemyJsonEntity);
            concepts.add(concept);
            associationService.createAssociationFor(new Tag(alchemyJsonEntity.text), concept.getId());
            if (!alchemyJsonEntity.disambiguated.name.isEmpty() && alchemyJsonEntity.disambiguated.name != alchemyJsonEntity.text) {
                associationService.createAssociationFor(new Tag(alchemyJsonEntity.disambiguated.name), concept.getId());
            }
        } else {
            createMissingsAssociations(alchemyJsonEntity, id);
        }
    }

    private void createMissingsAssociations(final AlchemyJsonEntity alchemyJsonEntity, final UUID id) {
        tryToCreateTag(alchemyJsonEntity.text, id);
        if (!alchemyJsonEntity.disambiguated.name.isEmpty() && alchemyJsonEntity.disambiguated.name != alchemyJsonEntity.text) {
            tryToCreateTag(alchemyJsonEntity.disambiguated.name, id);
        }
    }

    private void tryToCreateTag(final String text, final UUID id) {
        try {
            associationService.lookUp(new Tag(text));
        } catch (AssociationNotFound e) {
            associationService.createAssociationFor(new Tag(text), id);
        }
    }

    private UUID findExistingConcept(final AlchemyJsonEntity alchemyJsonEntity) {
        try {
            return associationService.lookUp(new Tag(alchemyJsonEntity.text)).getSubjectId();
        } catch (AssociationNotFound textNotFound) {
            if (!alchemyJsonEntity.disambiguated.name.isEmpty() && alchemyJsonEntity.disambiguated.name != alchemyJsonEntity.text) {
                try {
                    return associationService.lookUp(new Tag(alchemyJsonEntity.disambiguated.name)).getSubjectId();
                } catch (AssociationNotFound nameNotFound) {
                    return null;
                }
            }
        }
        return null;
    }

    private Concept createConcept(final AlchemyJsonEntity alchemyJsonEntity) {
        final Concept concept = new ConceptFactory().newConcept(alchemyJsonEntity);
        Repositories.subjects().add(concept);
        return concept;
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
    private AssociationService associationService;
    private List<Concept> concepts = Lists.newArrayList();
}
