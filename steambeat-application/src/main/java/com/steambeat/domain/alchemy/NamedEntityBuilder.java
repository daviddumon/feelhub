package com.steambeat.domain.alchemy;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.association.tag.Tag;
import com.steambeat.domain.thesaurus.Language;

import java.util.UUID;
import java.util.regex.*;

public class NamedEntityBuilder {

    @Inject
    public NamedEntityBuilder(final AssociationService associationService) {
        this.associationService = associationService;
    }

    public NamedEntity build(final AlchemyJsonEntity alchemyJsonEntity) {
        final NamedEntity entity = new NamedEntity();
        addKeywords(entity, alchemyJsonEntity);
        addFields(entity, alchemyJsonEntity);
        addName(entity, alchemyJsonEntity);
        addConcept(entity, alchemyJsonEntity);
        return entity;
    }

    private void addKeywords(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        addKeywordFromText(entity, alchemyJsonEntity);
        addKeywordFromName(entity, alchemyJsonEntity);
    }

    private void addKeywordFromText(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        try {
            checkForSize(alchemyJsonEntity.text);
            checkForSpecialCharacters(alchemyJsonEntity.text);
            entity.keywords.add(alchemyJsonEntity.text);
        } catch (Exception e) {
        }
    }

    private void addKeywordFromName(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        try {
            checkForEmptyDisambiguatedName(alchemyJsonEntity);
            checkForSameTextAndName(alchemyJsonEntity);
            checkForSize(alchemyJsonEntity.disambiguated.name);
            checkForSpecialCharacters(alchemyJsonEntity.disambiguated.name);
            entity.keywords.add(alchemyJsonEntity.disambiguated.name);
        } catch (Exception e) {
        }
    }

    private void checkForSize(final String text) throws Exception {
        if (text.length() < TOO_SMALL) {
            throw new Exception();
        }
    }

    private void checkForSpecialCharacters(final String text) throws Exception {
        Pattern specialCharactersChecker = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = specialCharactersChecker.matcher(text);
        if (matcher.find()) {
            throw new Exception();
        }
    }

    private void checkForEmptyDisambiguatedName(final AlchemyJsonEntity alchemyJsonEntity) throws Exception {
        if (alchemyJsonEntity.disambiguated.name.isEmpty()) {
            throw new Exception();
        }
    }

    private void checkForSameTextAndName(final AlchemyJsonEntity alchemyJsonEntity) throws Exception {
        if (alchemyJsonEntity.disambiguated.name.equals(alchemyJsonEntity.text)) {
            throw new Exception();
        }
    }

    private void addFields(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        entity.type = alchemyJsonEntity.type;
        entity.language = Language.forString(alchemyJsonEntity.language);
        entity.relevance = alchemyJsonEntity.relevance;
        if (isDisambiguated(alchemyJsonEntity)) {
            entity.subType = alchemyJsonEntity.disambiguated.subType;
            entity.website = alchemyJsonEntity.disambiguated.website;
            entity.geo = alchemyJsonEntity.disambiguated.geo;
        }
    }

    private boolean isDisambiguated(final AlchemyJsonEntity alchemyJsonEntity) {
        return alchemyJsonEntity.disambiguated != null;
    }

    private void addName(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        if (alchemyJsonEntity.disambiguated.name.isEmpty()) {
            entity.name = alchemyJsonEntity.text;
        } else {
            entity.name = alchemyJsonEntity.disambiguated.name;
        }
    }

    private void addConcept(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        entity.conceptId = findConceptForBothEntities(alchemyJsonEntity);
    }

    private UUID findConceptForBothEntities(final AlchemyJsonEntity alchemyJsonEntity) {
        try {
            return associationService.lookUp(new Tag(alchemyJsonEntity.text)).getSubjectId();
        } catch (AssociationNotFound textNotFound) {
            if (!alchemyJsonEntity.disambiguated.name.isEmpty() && alchemyJsonEntity.disambiguated.name != alchemyJsonEntity.text) {
                try {
                    return associationService.lookUp(new Tag(alchemyJsonEntity.disambiguated.name)).getSubjectId();
                } catch (AssociationNotFound nameNotFound) {
                }
            }
        }
        return null;
    }

    private AssociationService associationService;

    private static int TOO_SMALL = 3;
}
