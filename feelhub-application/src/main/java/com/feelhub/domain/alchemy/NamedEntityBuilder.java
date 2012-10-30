package com.feelhub.domain.alchemy;

import com.feelhub.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

import java.util.regex.*;

public class NamedEntityBuilder {

    public NamedEntity build(final AlchemyJsonEntity alchemyJsonEntity) {
        final NamedEntity entity = new NamedEntity();
        addKeywords(entity, alchemyJsonEntity);
        if (entity.keywords.isEmpty()) {
            return null;
        }
        addFields(entity, alchemyJsonEntity);
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
            entity.keywords.add(alchemyJsonEntity.text.trim());
        } catch (Exception e) {
        }
    }

    private void addKeywordFromName(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        try {
            checkForSameTextAndName(alchemyJsonEntity);
            checkForSize(alchemyJsonEntity.disambiguated.name);
            checkForSpecialCharacters(alchemyJsonEntity.disambiguated.name);
            entity.keywords.add(alchemyJsonEntity.disambiguated.name.trim());
        } catch (Exception e) {
        }
    }

    private void checkForSize(final String text) throws Exception {
        final int TOO_SMALL = 3;
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
            entity.opencyc = alchemyJsonEntity.disambiguated.opencyc;
            entity.census = alchemyJsonEntity.disambiguated.census;
            entity.ciaFactbook = alchemyJsonEntity.disambiguated.ciaFactbook;
            entity.crunchbase = alchemyJsonEntity.disambiguated.crunchbase;
            entity.semanticCrunchbase = alchemyJsonEntity.disambiguated.semanticCrunchbase;
            entity.musicBrainz = alchemyJsonEntity.disambiguated.musicBrainz;
            entity.dbpedia = alchemyJsonEntity.disambiguated.dbpedia;
            entity.freebase = alchemyJsonEntity.disambiguated.freebase;
            entity.geonames = alchemyJsonEntity.disambiguated.geonames;
            entity.yago = alchemyJsonEntity.disambiguated.yago;
            entity.umbel = alchemyJsonEntity.disambiguated.umbel;
        }
    }

    private boolean isDisambiguated(final AlchemyJsonEntity alchemyJsonEntity) {
        return alchemyJsonEntity.disambiguated != null;
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
                || alchemyJsonEntity.type.equalsIgnoreCase("Brand")
                ) {
            entity.feelhubLanguage = FeelhubLanguage.none();
        } else {
            entity.feelhubLanguage = FeelhubLanguage.forString(alchemyJsonEntity.language);
        }
    }

}
