package com.feelhub.domain.alchemy;

import com.feelhub.domain.alchemy.readmodel.AlchemyJsonEntity;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.real.RealTopicType;
import org.apache.commons.lang.WordUtils;

import java.util.regex.*;

public class NamedEntityFactory {

    public NamedEntity build(final AlchemyJsonEntity alchemyJsonEntity) {
        final NamedEntity entity = new NamedEntity();
        addTags(entity, alchemyJsonEntity);
        if (entity.tags.isEmpty()) {
            return null;
        }
        addFields(entity, alchemyJsonEntity);
        addLanguage(entity, alchemyJsonEntity);
        return entity;
    }

    private void addTags(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        addTagFromText(entity, alchemyJsonEntity);
        addTagFromName(entity, alchemyJsonEntity);
    }

    private void addTagFromText(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        try {
            checkForSize(alchemyJsonEntity.text);
            checkForSpecialCharacters(alchemyJsonEntity.text);
            entity.tags.add(alchemyJsonEntity.text.trim());
        } catch (Exception e) {
        }
    }

    private void addTagFromName(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        try {
            checkForSameTextAndName(alchemyJsonEntity);
            checkForSize(alchemyJsonEntity.disambiguated.name);
            checkForSpecialCharacters(alchemyJsonEntity.disambiguated.name);
            entity.tags.add(alchemyJsonEntity.disambiguated.name.trim());
        } catch (Exception e) {
        }
    }

    private void checkForSize(final String text) throws Exception {
        final int TOO_SMALL = 2;
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
        try {
            entity.type = RealTopicType.valueOf(WordUtils.capitalizeFully(alchemyJsonEntity.type));
        } catch (IllegalArgumentException e) {
            entity.type = RealTopicType.Other;
        }
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
        try {
            addLanguageForTopicType(entity, alchemyJsonEntity);
        } catch (IllegalArgumentException e) {
            entity.feelhubLanguage = FeelhubLanguage.none();
        }
    }

    private void addLanguageForTopicType(final NamedEntity entity, final AlchemyJsonEntity alchemyJsonEntity) {
        if (RealTopicType.valueOf(WordUtils.capitalizeFully(alchemyJsonEntity.type)).isTranslatable()) {
            entity.feelhubLanguage = FeelhubLanguage.fromCountryName(alchemyJsonEntity.language);
        } else {
            entity.feelhubLanguage = FeelhubLanguage.none();
        }
    }
}
