package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.usable.UsableTopic;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicRelatedResource extends ServerResource {

    @Inject
    public ApiTopicRelatedResource(final RelationSearch relationSearch, final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.relationSearch = relationSearch;
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView getRelatedTopics() {
        getTopic();
        doSearchWithQueryParameters();
        getTopicDataForEachRelation();
        return ModelAndView.createNew("api/related.json.ftl", MediaType.APPLICATION_JSON).with("topicDataList", topicDataList);
    }

    private void getTopic() {
        try {
            topicId = getRequestAttributes().get("topicId").toString().trim();
            realTopic = topicService.lookUp(UUID.fromString(topicId));
            relationSearch.withTopicId(UUID.fromString(topicId));
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private void doSearchWithQueryParameters() {
        final Form form = getQuery();
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        relations = relationSearch.withSort("weight", Search.REVERSE_ORDER).execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubApiException();
            }
            relationSearch.withLimit(limit);
        } else {
            relationSearch.withLimit(100);
        }
    }

    private void setUpSearchForSkipParameter(final Form form) {
        if (form.getQueryString().contains("skip")) {
            relationSearch.withSkip(Integer.parseInt(form.getFirstValue("skip").trim()));
        } else {
            relationSearch.withSkip(0);
        }
    }

    private void getTopicDataForEachRelation() {
        for (final Relation relation : relations) {
            addTopicData(relation);
        }
    }

    private void addTopicData(final Relation relation) {
        try {
            final UsableTopic realTopic = (UsableTopic) topicService.lookUp(relation.getToId());
            final TopicData topicData = topicDataFactory.getTopicData(realTopic, CurrentUser.get().getLanguage());
            topicDataList.add(topicData);
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private String topicId;
    private final RelationSearch relationSearch;
    private final TopicService topicService;
    private final TopicDataFactory topicDataFactory;
    private List<Relation> relations = Lists.newArrayList();
    private final List<TopicData> topicDataList = Lists.newArrayList();
    private Topic realTopic;
}
