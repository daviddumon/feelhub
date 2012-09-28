package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.illustration.Illustration;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.repositories.Repositories;
import com.steambeat.web.dto.ReferenceData;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class KeywordResource extends ServerResource {

    @Inject
    public KeywordResource(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @Get
    public Representation get() {
        extractLanguageFromUri();
        extractKeywordValueFromUri();
        Keyword keyword;
        try {
            keyword = keywordService.lookUp(keywordValue, steambeatLanguage);
        } catch (KeywordNotFound e) {
            keyword = new Keyword(keywordValue, steambeatLanguage, null);
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
        }
        return SteambeatTemplateRepresentation.createNew("main.ftl", getContext(), getRequest()).with("referenceData", getReferenceData(keyword));
    }

    private void extractLanguageFromUri() {
        if (getRequestAttributes().containsKey("language")) {
            steambeatLanguage = SteambeatLanguage.forString(getRequestAttributes().get("language").toString());
        } else {
            steambeatLanguage = SteambeatLanguage.none();
        }
    }

    private void extractKeywordValueFromUri() {
        keywordValue = getRequestAttributes().get("keyword").toString();
    }

    private ReferenceData getReferenceData(final Keyword keyword) {
        ReferenceData.Builder builder = new ReferenceData.Builder();
        builder.keyword(keyword);
        builder.language(keyword.getLanguage());
        if (keyword.getReferenceId() != null) {
            builder.referenceId(keyword.getReferenceId());
        }
        final List<Illustration> illustrations = Repositories.illustrations().forReferenceId(keyword.getReferenceId());
        if (!illustrations.isEmpty()) {
            builder.illustration(illustrations.get(0));
        }
        return builder.build();
    }

    private KeywordService keywordService;
    private SteambeatLanguage steambeatLanguage;
    private String keywordValue;
}
