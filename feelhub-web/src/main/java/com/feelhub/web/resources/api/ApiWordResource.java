package com.feelhub.web.resources.api;

import com.feelhub.application.WordService;
import com.feelhub.domain.keyword.word.*;
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
        final String keywordValue = parameters.getFirstValue("keywordValue").toString().trim();
        final String languageCode = parameters.getFirstValue("languageCode").toString().trim();
        try {
            final Word word = wordService.lookUp(keywordValue, FeelhubLanguage.forString(languageCode));
            setStatus(Status.SUCCESS_OK);
            return ModelAndView.createNew("api/word.json.ftl", MediaType.APPLICATION_JSON).with("topicId", word.getTopicId());
        } catch (WordNotFound e) {
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ModelAndView.createNew("api/word.json.ftl", MediaType.APPLICATION_JSON);
        }
    }

    private WordService wordService;
}
