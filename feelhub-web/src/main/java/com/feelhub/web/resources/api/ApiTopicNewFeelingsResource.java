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
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicNewFeelingsResource extends ServerResource {

    @Inject
    public ApiTopicNewFeelingsResource(final TopicDataFactory topicDataFactory, final FeelingSearch feelingSearch, final TopicService topicService) {
        this.topicDataFactory = topicDataFactory;
        this.feelingSearch = feelingSearch;
        this.topicService = topicService;
    }

    @Get
    public ModelAndView represent() {
        final List<Feeling> feelings = doSearchWithQueryParameters();
        final List<FeelingData> feelingDatas = getFeelingDatas(feelings);
        setStatus(Status.SUCCESS_OK);
        return ModelAndView.createNew("api/feelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
    }

    private List<Feeling> doSearchWithQueryParameters() {
        final List<Feeling> feelings = Lists.newArrayList();
        setUpSearchForTopicId();
        getLastFeeling();
        feelingSearch.withSkip(0);
        feelingSearch.withLimit(30);
        final List<Feeling> searchResult = feelingSearch.withSort("creationDate", FeelingSearch.REVERSE_ORDER).execute();
        int i = 0;
        boolean found = false;
        while (!found & i < searchResult.size()) {
            if (searchResult.get(i).getId().equals(lastFeelingId)) {
                found = true;
            } else {
                feelings.add(searchResult.get(i++));
                if (i % 30 == 0) {
                    feelingSearch.reset();
                    feelingSearch.withSkip(i).withLimit(30).withSort("creationDate", FeelingSearch.REVERSE_ORDER).withTopicId(topic.getId());
                    searchResult.addAll(feelingSearch.execute());
                }
            }
        }
        return feelings;
    }

    private void setUpSearchForTopicId() {
        try {
            final String topicId = getRequestAttributes().get("topicId").toString().trim();
            topic = topicService.lookUp(UUID.fromString(topicId));
            feelingSearch.withTopicId(topic.getId());
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private void getLastFeeling() {
        lastFeelingId = UUID.fromString(getRequestAttributes().get("feelingId").toString());
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

    private final TopicDataFactory topicDataFactory;
    private final FeelingSearch feelingSearch;
    private final TopicService topicService;
    private UUID lastFeelingId;
    private Topic topic;
}
