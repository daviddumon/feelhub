package com.feelhub.web.resources.json;

import com.feelhub.application.KeywordService;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

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
            final Keyword keyword = keywordService.lookUp(keywordValue, FeelhubLanguage.forString(languageCode));
            setStatus(Status.SUCCESS_OK);
            return ModelAndView.createNew("json/keyword.json.ftl", MediaType.APPLICATION_JSON).with("topicId", keyword.getTopicId());
        } catch (KeywordNotFound e) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ModelAndView.createNew("json/keyword.json.ftl", MediaType.APPLICATION_JSON);
        }
    }

    private KeywordService keywordService;
}
