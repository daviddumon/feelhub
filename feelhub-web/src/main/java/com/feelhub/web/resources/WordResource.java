package com.feelhub.web.resources;

import com.feelhub.application.WordService;
import com.feelhub.domain.keyword.KeywordNotFound;
import com.feelhub.domain.keyword.word.Word;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.dto.KeywordDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class WordResource extends ServerResource {

    @Inject
    public WordResource(final WordService wordService, final KeywordDataFactory keywordDataFactory) {
        this.wordService = wordService;
        this.keywordDataFactory = keywordDataFactory;
    }

    @Get
    public ModelAndView represent() {
        extractLanguageFromUri();
        extractWordValueFromUri();
        Word word;
        try {
            word = wordService.lookUp(keywordValue, feelhubLanguage);
            return ModelAndView.createNew("keyword.ftl").with("keywordData", keywordDataFactory.getKeywordData(word));
        } catch (KeywordNotFound e) {
            word = new Word(keywordValue, feelhubLanguage, null);
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ModelAndView.createNew("404.ftl").with("keywordData", keywordDataFactory.getKeywordData(word));
        }
    }

    private void extractLanguageFromUri() {
        if (getRequestAttributes().containsKey("language")) {
            feelhubLanguage = FeelhubLanguage.forString(getRequestAttributes().get("language").toString());
        } else {
            feelhubLanguage = FeelhubLanguage.none();
        }
    }

    private void extractWordValueFromUri() {
        try {
            keywordValue = URLDecoder.decode(getRequestAttributes().get("keywordValue").toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private WordService wordService;
    private final KeywordDataFactory keywordDataFactory;
    private FeelhubLanguage feelhubLanguage;
    private String keywordValue;
}
