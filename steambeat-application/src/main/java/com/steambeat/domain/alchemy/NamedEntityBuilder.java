package com.steambeat.domain.alchemy;

import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.steambeat.domain.thesaurus.Language;

import java.util.UUID;
import java.util.regex.*;

public class NamedEntityBuilder {

    @Inject
    public NamedEntityBuilder(final KeywordService associationService) {
        this.associationService = associationService;
    }

    public NamedEntity build(final AlchemyJsonEntity alchemyJsonEntity) {
        final NamedEntity entity = new NamedEntity();
        addKeywords(entity, alchemyJsonEntity);
        addFields(entity, alchemyJsonEntity);
        addName(entity, alchemyJsonEntity);
        addConcept(entity, alchemyJsonEntity);
        addLanguage(entity, alchemyJsonEntity);
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
        final Pattern specialCharactersChecker = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
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
        final Language language = Language.forString(alchemyJsonEntity.language);
        try {
            return associationService.lookUp(alchemyJsonEntity.text, language).getTopic();
        } catch (AssociationNotFound textNotFound) {
            if (!alchemyJsonEntity.disambiguated.name.isEmpty() && alchemyJsonEntity.disambiguated.name != alchemyJsonEntity.text) {
                try {
                    return associationService.lookUp(alchemyJsonEntity.disambiguated.name, language).getTopic();
                } catch (AssociationNotFound nameNotFound) {
                }
            }
        }
        return null;
    }

    private void addLanguage(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        if (alchemyJsonEntity.type.equalsIgnoreCase("Automobile")
                || alchemyJsonEntity.type.equalsIgnoreCase("Company")
                || alchemyJsonEntity.type.equalsIgnoreCase("EntertainmentAward")
                || alchemyJsonEntity.type.equalsIgnoreCase("FinancialMarketIndex")
                || alchemyJsonEntity.type.equalsIgnoreCase("Movie")
                || alchemyJsonEntity.type.equalsIgnoreCase("MusicGroup")
                || alchemyJsonEntity.type.equalsIgnoreCase("OperatingSystem")
                || alchemyJsonEntity.type.equalsIgnoreCase("Organization")
                || alchemyJsonEntity.type.equalsIgnoreCase("Person")
                || alchemyJsonEntity.type.equalsIgnoreCase("PrintMedia")
                || alchemyJsonEntity.type.equalsIgnoreCase("Technology")
                || alchemyJsonEntity.type.equalsIgnoreCase("Movie")
                || alchemyJsonEntity.type.equalsIgnoreCase("TelevisionStation")
                ) {
            entity.language = Language.forString("");
        } else {
            entity.language = Language.forString(alchemyJsonEntity.language);
        }
    }

    private KeywordService associationService;

    private static int TOO_SMALL = 3;
}
