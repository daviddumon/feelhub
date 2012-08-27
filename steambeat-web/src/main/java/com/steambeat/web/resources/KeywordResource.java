package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

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
        return SteambeatTemplateRepresentation.createNew("keyword.ftl", getContext(), getRequest())
                .with("keyword", keyword)
                .with("language", steambeatLanguage);
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

    private KeywordService keywordService;
    private SteambeatLanguage steambeatLanguage;
    private String keywordValue;
}
