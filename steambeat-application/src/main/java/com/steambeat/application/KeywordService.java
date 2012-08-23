package com.steambeat.application;

import com.google.inject.Inject;
import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;

public class KeywordService {

    @Inject
    public KeywordService(final KeywordFactory keywordFactory, final ReferenceService referenceService) {
        this.keywordFactory = keywordFactory;
        this.referenceService = referenceService;
    }

    public Keyword lookUp(final String value, final SteambeatLanguage steambeatLanguage) {
        final Keyword keyword = Repositories.keywords().forValueAndLanguage(value, steambeatLanguage);
        if (keyword == null) {
            throw new KeywordNotFound();
        }
        return keyword;
    }

    public Keyword createKeyword(final String value, final SteambeatLanguage steambeatLanguage) {
        final Keyword keyword = createKeywordWithoutEvent(value, steambeatLanguage);
        postEvent(keyword);
        return keyword;
    }

    public Keyword createKeywordWithoutEvent(final String value, final SteambeatLanguage steambeatLanguage) {
        final Reference reference = referenceService.newReference();
        final Keyword keyword = keywordFactory.createKeyword(value, steambeatLanguage, reference.getId());
        Repositories.keywords().add(keyword);
        return keyword;
    }

    private void postEvent(final Keyword keyword) {
        DomainEventBus.INSTANCE.post(new KeywordCreatedEvent(keyword));
    }

    private KeywordFactory keywordFactory;
    private ReferenceService referenceService;

    //
    //public Association createAssociationFor(final Tag tag, final UUID id, final Language language) {
    //    addEnglishAssociationIfNeeded(tag, id, language);
    //    final Association association = new Association(tag, id, language);
    //    Repositories.associations().add(association);
    //    return association;
    //}
    //
    //private void addEnglishAssociationIfNeeded(final Tag tag, final UUID id, final Language language) {
    //    if (!language.getCode().equalsIgnoreCase("english") && !language.getCode().equalsIgnoreCase("")) {
    //        final String translatedTag = microsoftTranslator.translate(tag.toString(), language.getCode());
    //        createAssociationFor(new Tag(translatedTag), id, Language.forString("english"));
    //    }
    //}
    //
    //public Association createAssociationsFor(final Uri uri) {
    //    final List<Uri> path = pathResolver.resolve(uri);
    //    final UUID subjectId = generateSubjectId(lastFrom(path));
    //    final List<Association> associations = createAssociations(path, subjectId);
    //    return lastAssociation(associations);
    //}
    //
    //private UUID generateSubjectId(final Uri canonicalAddress) {
    //    final Association foundAssociation = Repositories.associations().forIdentifier(canonicalAddress);
    //    if (foundAssociation != null) {
    //        return foundAssociation.getSubjectId();
    //    }
    //    return UUID.randomUUID();
    //}
    //
    //private Uri lastFrom(final List<Uri> path) {
    //    return path.get(path.size() - 1);
    //}
    //
    //private List<Association> createAssociations(final List<Uri> path, final UUID subjectId) {
    //    final ArrayList<Association> associations = Lists.newArrayList();
    //    for (final Uri uri : path) {
    //        Association association;
    //        try {
    //            association = lookUp(uri);
    //        } catch (AssociationNotFound e) {
    //            association = new Association(uri, subjectId, Language.forString(""));
    //            Repositories.associations().add(association);
    //        }
    //        associations.add(association);
    //    }
    //    return associations;
    //}
    //
    //private Association lastAssociation(final List<Association> associations) {
    //    return associations.get(associations.size() - 1);
    //}
}
