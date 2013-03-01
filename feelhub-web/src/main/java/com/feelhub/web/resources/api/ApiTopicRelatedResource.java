package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.related.Related;
import com.feelhub.domain.topic.*;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.RelatedSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.Order;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicRelatedResource extends ServerResource {

    @Inject
    public ApiTopicRelatedResource(final RelatedSearch relatedSearch, final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.relatedSearch = relatedSearch;
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView getRelatedTopics() {
        getTopic();
        doSearchWithQueryParameters();
        getTopicDataForEachRelated();
        return ModelAndView.createNew("api/related.json.ftl", MediaType.APPLICATION_JSON).with("topicDataList", topicDataList);
    }

    private void getTopic() {
        try {
            final String topicId = getRequestAttributes().get("topicId").toString().trim();
            final Topic topic = topicService.lookUpCurrent(UUID.fromString(topicId));
            relatedSearch.withTopicId(topic.getCurrentId());
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private void doSearchWithQueryParameters() {
        final Form form = getQuery();
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        relateds = relatedSearch.withSort("weight", Order.DESCENDING).execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubApiException();
            }
            relatedSearch.withLimit(limit);
        } else {
            relatedSearch.withLimit(100);
        }
    }

    private void setUpSearchForSkipParameter(final Form form) {
        if (form.getQueryString().contains("skip")) {
            relatedSearch.withSkip(Integer.parseInt(form.getFirstValue("skip").trim()));
        } else {
            relatedSearch.withSkip(0);
        }
    }

    private void getTopicDataForEachRelated() {
        for (final Related related : relateds) {
            addTopicData(related);
        }
    }

    private void addTopicData(final Related related) {
        try {
            final Topic topic = topicService.lookUpCurrent(related.getToId());
            final TopicData topicData = topicDataFactory.simpleTopicData(topic, CurrentUser.get().getLanguage());
            topicDataList.add(topicData);
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private final RelatedSearch relatedSearch;
    private final TopicService topicService;
    private final TopicDataFactory topicDataFactory;
    private List<Related> relateds = Lists.newArrayList();
    private final List<TopicData> topicDataList = Lists.newArrayList();
}
