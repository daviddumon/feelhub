package com.feelhub.web.resources.api.topics;

import com.feelhub.application.search.TopicSearch;
import com.feelhub.domain.topic.*;
import com.feelhub.web.api.ApiFeelingSearch;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicFeelingsResource extends ServerResource {

    @Inject
    public ApiTopicFeelingsResource(final TopicSearch topicSearch, final ApiFeelingSearch apiFeelingSearch) {
        this.topicSearch = topicSearch;
        this.apiFeelingSearch = apiFeelingSearch;
    }

    @Get
    public ModelAndView getFeelings() {
        try {
            final List<FeelingData> feelingDatas = apiFeelingSearch.doSearchForATopic(extractTopic(), getQuery());
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
        return topicSearch.lookUpCurrent(UUID.fromString(topicId));
    }

    private final ApiFeelingSearch apiFeelingSearch;
    private final TopicSearch topicSearch;
}
