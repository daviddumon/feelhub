package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.topic.*;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.json.JSONException;
import org.restlet.data.MediaType;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicFeelingsResource extends ServerResource {

    @Inject
    public ApiTopicFeelingsResource(final TopicService topicService, final ApiFeelingSearch apiFeelingSearch) {
        this.topicService = topicService;
        this.apiFeelingSearch = apiFeelingSearch;
    }

    @Get
    public ModelAndView getFeelings() throws JSONException {
        final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(extractTopic(), getQuery());
        return ModelAndView.createNew("api/feelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
    }

    private Topic extractTopic() {
        try {
            final String topicId = getRequestAttributes().get("topicId").toString().trim();
            return topicService.lookUpCurrent(UUID.fromString(topicId));
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private final ApiFeelingSearch apiFeelingSearch;
    private final TopicService topicService;
}
