package com.feelhub.web.resources.api.topics;

import com.feelhub.application.TopicService;
import com.feelhub.domain.topic.*;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.feelhub.web.resources.api.feelings.ApiFeelingSearch;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicFeelingsResource extends ServerResource {

    @Inject
    public ApiTopicFeelingsResource(final TopicService topicService, final ApiFeelingSearch apiFeelingSearch) {
        this.topicService = topicService;
        this.apiFeelingSearch = apiFeelingSearch;
    }

    @Get
    public ModelAndView getFeelings() {
        try {
            final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(extractTopic(), getQuery());
            return ModelAndView.createNew("api/feelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
        } catch (TopicNotFound e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        }
    }

    private Topic extractTopic() {
        final String topicId = getRequestAttributes().get("topicId").toString().trim();
        return topicService.lookUpCurrent(UUID.fromString(topicId));
    }

    private final ApiFeelingSearch apiFeelingSearch;
    private final TopicService topicService;
}
