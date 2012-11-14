package com.feelhub.web.resources;

import com.feelhub.application.UriService;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

public class UriResource extends ServerResource {

    @Inject
    public UriResource(final UriService uriService, final TopicDataFactory topicDataFactory) {
        this.uriService = uriService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView represent() {
        extractUriValueFromUri();
        Uri uri;
        try {
            uri = uriService.lookUp(value);
            return ModelAndView.createNew("keyword.ftl").with("topicData", topicDataFactory.getTopicData(uri));
        } catch (UriNotFound e) {
            uri = new Uri("", null);
            setStatus(Status.CLIENT_ERROR_NOT_FOUND);
            return ModelAndView.createNew("404.ftl").with("topicData", topicDataFactory.getTopicData(uri));
        }
    }

    private void extractUriValueFromUri() {
        value = getRequestAttributes().get("value").toString();
    }

    private UriService uriService;
    private TopicDataFactory topicDataFactory;
    private String value;
}
