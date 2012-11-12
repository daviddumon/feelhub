package com.feelhub.web.resources;

import com.feelhub.application.KeywordService;
import com.feelhub.domain.keyword.*;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

public class KeywordResource extends ServerResource {

    @Inject
    public KeywordResource(final KeywordService keywordService, final TopicDataFactory topicDataFactory) {
        this.keywordService = keywordService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView represent() {
        extractLanguageFromUri();
        extractKeywordValueFromUri();
        Keyword keyword;
        try {
            keyword = keywordService.lookUp(keywordValue, feelhubLanguage);
            return ModelAndView.createNew("keyword.ftl").with("topicData", topicDataFactory.getTopicData(keyword));
        } catch (KeywordNotFound e) {
            keyword = new Word(keywordValue, feelhubLanguage, null);
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ModelAndView.createNew("404.ftl").with("topicData", topicDataFactory.getTopicData(keyword));
        }
    }

    private void extractLanguageFromUri() {
        if (getRequestAttributes().containsKey("language")) {
            feelhubLanguage = FeelhubLanguage.forString(getRequestAttributes().get("language").toString());
        } else {
            feelhubLanguage = FeelhubLanguage.none();
        }
    }

    private void extractKeywordValueFromUri() {
        keywordValue = getRequestAttributes().get("keyword").toString();
    }

    private final KeywordService keywordService;
    private final TopicDataFactory topicDataFactory;
    private FeelhubLanguage feelhubLanguage;
    private String keywordValue;
}
