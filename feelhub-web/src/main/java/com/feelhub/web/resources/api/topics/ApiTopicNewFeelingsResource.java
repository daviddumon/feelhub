package com.feelhub.web.resources.api.topics;

import com.feelhub.application.search.TopicSearch;
import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.*;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.feelhub.web.search.FeelingSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.Order;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicNewFeelingsResource extends ServerResource {

    @Inject
    public ApiTopicNewFeelingsResource(final FeelingDataFactory feelingDataFactory, final FeelingSearch feelingSearch, final TopicSearch topicSearch) {
        this.feelingDataFactory = feelingDataFactory;
        this.feelingSearch = feelingSearch;
        this.topicSearch = topicSearch;
    }

    @Get
    public ModelAndView represent() {
        try {
            final List<Feeling> feelings = doSearchWithQueryParameters();
            final List<FeelingData> feelingDatas = feelingDataFactory.feelingDatas(feelings);
            setStatus(Status.SUCCESS_OK);
            return ModelAndView.createNew("api/feelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
        } catch (TopicNotFound e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        }
    }

    private List<Feeling> doSearchWithQueryParameters() {
        final List<Feeling> feelings = Lists.newArrayList();
        setUpSearchForTopicId();
        getLastFeeling();
        feelingSearch.withSkip(0);
        feelingSearch.withLimit(30);
        final List<Feeling> searchResult = feelingSearch.withSort("creationDate", Order.DESCENDING).execute();
        int i = 0;
        boolean found = false;
        while (!found & i < searchResult.size()) {
            if (searchResult.get(i).getId().equals(lastFeelingId)) {
                found = true;
            } else {
                feelings.add(searchResult.get(i++));
                if (i % 30 == 0) {
                    feelingSearch.reset();
                    feelingSearch.withSkip(i).withLimit(30).withSort("creationDate", Order.DESCENDING).withTopicId(realTopic.getId());
                    searchResult.addAll(feelingSearch.execute());
                }
            }
        }
        return feelings;
    }

    private void setUpSearchForTopicId() {
        final String topicId = getRequestAttributes().get("topicId").toString().trim();
        realTopic = topicSearch.lookUpCurrent(UUID.fromString(topicId));
        feelingSearch.withTopicId(realTopic.getId());
    }

    private void getLastFeeling() {
        lastFeelingId = UUID.fromString(getRequestAttributes().get("feelingId").toString());
    }

    private final FeelingDataFactory feelingDataFactory;
    private final FeelingSearch feelingSearch;
    private final TopicSearch topicSearch;
    private UUID lastFeelingId;
    private Topic realTopic;
}
