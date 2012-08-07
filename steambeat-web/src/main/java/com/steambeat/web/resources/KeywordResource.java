package com.steambeat.web.resources;

import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.thesaurus.Language;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
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
        final Keyword keyword = keywordService.lookUp(keywordValue, language);
        return SteambeatTemplateRepresentation.createNew("keyword.ftl", getContext())
                .with("keyword", keyword)
                .with("language", language);
    }

    private void extractLanguageFromUri() {
        if (getRequestAttributes().containsKey("language")) {
            language = Language.forString(getRequestAttributes().get("language").toString());
        } else {
            language = Language.forString("none");
        }
    }

    private void extractKeywordValueFromUri() {
        keywordValue = getRequestAttributes().get("keyword").toString();
    }

    private KeywordService keywordService;
    private Language language;
    private String keywordValue;
}
