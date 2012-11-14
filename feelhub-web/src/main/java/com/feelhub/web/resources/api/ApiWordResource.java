package com.feelhub.web.resources.api;

import com.feelhub.application.WordService;
import com.feelhub.domain.keyword.KeywordNotFound;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

public class ApiWordResource extends ServerResource {

    @Inject
    public ApiWordResource(final WordService wordService) {
        this.wordService = wordService;
    }

    @Get
    public ModelAndView getId() {
        final Form parameters = getQuery();
        final String value = parameters.getFirstValue("value").toString().trim();
        final String languageCode = parameters.getFirstValue("languageCode").toString().trim();
        try {
            final Word word = wordService.lookUp(value, FeelhubLanguage.forString(languageCode));
            setStatus(Status.SUCCESS_OK);
            return ModelAndView.createNew("api/word.json.ftl", MediaType.APPLICATION_JSON).with("topicId", word.getTopicId());
        } catch (KeywordNotFound e) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ModelAndView.createNew("api/word.json.ftl", MediaType.APPLICATION_JSON);
        }
    }

    private WordService wordService;
}
