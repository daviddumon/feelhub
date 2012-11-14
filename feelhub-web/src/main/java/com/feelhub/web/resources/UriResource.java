package com.feelhub.web.resources;

import com.feelhub.application.UriService;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.web.dto.KeywordDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

public class UriResource extends ServerResource {

    @Inject
    public UriResource(final UriService uriService, final KeywordDataFactory keywordDataFactory) {
        this.uriService = uriService;
        this.keywordDataFactory = keywordDataFactory;
    }

    @Get
    public ModelAndView represent() {
        extractUriValueFromUri();
        Uri uri;
        try {
            uri = uriService.lookUp(value);
            return ModelAndView.createNew("keyword.ftl").with("keywordData", keywordDataFactory.getKeywordData(uri));
        } catch (UriNotFound e) {
            uri = new Uri("", null);
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ModelAndView.createNew("404.ftl").with("keywordData", keywordDataFactory.getKeywordData(uri));
        }
    }

    private void extractUriValueFromUri() {
        value = getRequestAttributes().get("value").toString();
    }

    private UriService uriService;
    private KeywordDataFactory keywordDataFactory;
    private String value;
}
