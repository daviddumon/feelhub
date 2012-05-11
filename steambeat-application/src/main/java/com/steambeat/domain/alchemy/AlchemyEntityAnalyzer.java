package com.steambeat.domain.alchemy;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.relation.*;
import com.steambeat.domain.subject.concept.*;
import com.steambeat.domain.subject.webpage.WebPage;
import com.steambeat.repositories.Repositories;

import java.util.*;
import java.util.regex.*;

public class AlchemyEntityAnalyzer {

    @Inject
    public AlchemyEntityAnalyzer(final AlchemyEntityProvider alchemyEntityProvider, final AssociationService associationService) {
        this.provider = alchemyEntityProvider;
        this.associationService = associationService;
    }

    public void analyze(final WebPage webpage) {
        final List<AlchemyJsonEntity> entities = provider.entitiesFor(webpage);
        analyzeEntities(entities, webpage);
        createRelationsForConcepts();
    }

    private void analyzeEntities(final List<AlchemyJsonEntity> entities, final WebPage webpage) {
        for (final AlchemyJsonEntity alchemyJsonEntity : entities) {
            if (checkEntity(alchemyJsonEntity)) {
                final UUID conceptId = findOrCreateAssociationAndConceptFor(alchemyJsonEntity);
                relationBuilder.connectTwoWays(webpage, new Concept(conceptId), alchemyJsonEntity.relevance);
            }
        }
    }

    private boolean checkEntity(final AlchemyJsonEntity alchemyJsonEntity) {
        if (checkForSmallEntity(alchemyJsonEntity)) {
            Pattern specialCharactersChecker = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            final Matcher textChecker = specialCharactersChecker.matcher(alchemyJsonEntity.text);
            final Matcher nameChecker = specialCharactersChecker.matcher(alchemyJsonEntity.disambiguated.name);
            if (textChecker.find() || nameChecker.find()) {
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean checkForSmallEntity(final AlchemyJsonEntity alchemyJsonEntity) {
        if (alchemyJsonEntity.text.length() < 3 || alchemyJsonEntity.disambiguated.name.length() < 3) {
            return false;
        }
        return true;
    }

    private UUID findOrCreateAssociationAndConceptFor(final AlchemyJsonEntity alchemyJsonEntity) {
        final UUID id = findExistingConcept(alchemyJsonEntity);
        if (id == null) {
            final Concept concept = createConcept(alchemyJsonEntity);
            concepts.add(concept);
            associationService.createAssociationFor(new Tag(alchemyJsonEntity.text), concept.getId());
            if (!alchemyJsonEntity.disambiguated.name.isEmpty() && alchemyJsonEntity.disambiguated.name != alchemyJsonEntity.text) {
                associationService.createAssociationFor(new Tag(alchemyJsonEntity.disambiguated.name), concept.getId());
            }
            return concept.getId();
        } else {
            createMissingsAssociations(alchemyJsonEntity, id);
            return id;
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

    private void createMissingsAssociations(final AlchemyJsonEntity alchemyJsonEntity, final UUID id) {
        tryToCreateAssociation(alchemyJsonEntity.text, id);
        if (!alchemyJsonEntity.disambiguated.name.isEmpty() && alchemyJsonEntity.disambiguated.name != alchemyJsonEntity.text) {
            tryToCreateAssociation(alchemyJsonEntity.disambiguated.name, id);
        }
    }

    private void tryToCreateAssociation(final String text, final UUID id) {
        try {
            associationService.lookUp(new Tag(text));
        } catch (AssociationNotFound e) {
            associationService.createAssociationFor(new Tag(text), id);
        }
    }

    private Concept createConcept(final AlchemyJsonEntity alchemyJsonEntity) {
        final Concept concept = new ConceptFactory().newConcept(alchemyJsonEntity);
        Repositories.subjects().add(concept);
        return concept;
    }

    private void createRelationsForConcepts() {
        for (Concept concept : concepts) {
            for (int i = concepts.lastIndexOf(concept); i < concepts.size(); i++) {
                Concept otherConcept = concepts.get(i);
                if (!concept.equals(otherConcept)) {
                    relationBuilder.connectTwoWays(concept, otherConcept);
                }
            }
        }
    }

    private final RelationBuilder relationBuilder = new RelationBuilder(new RelationFactory());
    private final AlchemyEntityProvider provider;
    private AssociationService associationService;
    private List<Concept> concepts = Lists.newArrayList();
}
