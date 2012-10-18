package com.steambeat.web.resources.json;

import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.*;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

public class JsonKeywordResource extends ServerResource {

    @Inject
    public JsonKeywordResource(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @Get
    public Representation getId() {
        final Form parameters = getQuery();
        final String keywordValue = parameters.getFirstValue("keywordValue").toString().trim();
        final String languageCode = parameters.getFirstValue("languageCode").toString().trim();
        try {
            final Keyword keyword = keywordService.lookUp(keywordValue, SteambeatLanguage.forString(languageCode));
            setStatus(Status.SUCCESS_OK);
            return SteambeatTemplateRepresentation.createNew("json/keyword.json.ftl", getContext(), MediaType.APPLICATION_JSON, getRequest()).with("referenceId", keyword.getReferenceId());
        } catch (KeywordNotFound e) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return SteambeatTemplateRepresentation.createNew("json/keyword.json.ftl", getContext(), MediaType.APPLICATION_JSON, getRequest());
        }
    }

    private KeywordService keywordService;
}
