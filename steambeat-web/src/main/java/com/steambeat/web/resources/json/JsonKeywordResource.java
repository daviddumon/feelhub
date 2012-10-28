package com.steambeat.web.resources.json;

import com.google.inject.Inject;
import com.steambeat.application.KeywordService;
import com.steambeat.domain.keyword.Keyword;
import com.steambeat.domain.keyword.KeywordNotFound;
import com.steambeat.domain.thesaurus.SteambeatLanguage;
import com.steambeat.web.representation.ModelAndView;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class JsonKeywordResource extends ServerResource {

    @Inject
    public JsonKeywordResource(final KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @Get
    public ModelAndView getId() {
        final Form parameters = getQuery();
        final String keywordValue = parameters.getFirstValue("keywordValue").toString().trim();
        final String languageCode = parameters.getFirstValue("languageCode").toString().trim();
        try {
            final Keyword keyword = keywordService.lookUp(keywordValue, SteambeatLanguage.forString(languageCode));
            setStatus(Status.SUCCESS_OK);
            return ModelAndView.createNew("json/keyword.json.ftl", MediaType.APPLICATION_JSON).with("referenceId", keyword.getReferenceId());
        } catch (KeywordNotFound e) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ModelAndView.createNew("json/keyword.json.ftl", MediaType.APPLICATION_JSON);
        }
    }

    private KeywordService keywordService;
}
