package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.*;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.FeelingSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicFeelingsResource extends ServerResource {

    @Inject
    public ApiTopicFeelingsResource(final TopicService topicService, final FeelingSearch feelingSearch, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.feelingSearch = feelingSearch;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView represent() throws JSONException {
        extractTopic();
        final List<Feeling> feelings = doSearchWithQueryParameters();
        final List<FeelingData> feelingDatas = getFeelingDatas(feelings);
        return ModelAndView.createNew("api/feelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
    }

    private void extractTopic() {
        try {
            final String topicId = getRequestAttributes().get("topicId").toString().trim();
            topic = topicService.lookUp(UUID.fromString(topicId));
            feelingSearch.withTopicId(topic.getId());
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private List<Feeling> doSearchWithQueryParameters() {
        setUpSearchForLimitParameter(getQuery());
        setUpSearchForSkipParameter(getQuery());
        return feelingSearch.withSort("creationDate", FeelingSearch.REVERSE_ORDER).execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubApiException();
            }
            feelingSearch.withLimit(limit);
        } else {
            feelingSearch.withLimit(100);
        }
    }

    private void setUpSearchForSkipParameter(final Form form) {
        if (form.getQueryString().contains("skip")) {
            feelingSearch.withSkip(Integer.parseInt(form.getFirstValue("skip").trim()));
        } else {
            feelingSearch.withSkip(0);
        }
    }

    private List<FeelingData> getFeelingDatas(final List<Feeling> feelings) {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            final List<TopicData> topicDatas = topicDataFactory.getTopicDatas(feeling, CurrentUser.get().getLanguage());
            final FeelingData feelingData = new FeelingData(feeling, topicDatas);
            feelingDatas.add(feelingData);
        }
        return feelingDatas;
    }

    private final FeelingSearch feelingSearch;
    private final TopicDataFactory topicDataFactory;
    private final TopicService topicService;
    private Topic topic;
}
