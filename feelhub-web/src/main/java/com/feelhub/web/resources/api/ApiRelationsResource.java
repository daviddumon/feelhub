package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.relation.Relation;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiRelationsResource extends ServerResource {

    @Inject
    public ApiRelationsResource(final TopicService topicService, final RelationSearch relationSearch) {
        this.topicService = topicService;
        this.relationSearch = relationSearch;
    }

    @Get
    public ModelAndView represent() {
        doSearchWithQueryParameters();
        loadTopics();
        return ModelAndView.createNew("api/relations.json.ftl", MediaType.APPLICATION_JSON).with("topics", topics);
    }

    private void doSearchWithQueryParameters() {
        final Form form = getQuery();
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        setUpSearchForFromIdParameter(form);
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

    private void setUpSearchForFromIdParameter(final Form form) {
        if (form.getQueryString().contains("topicId")) {
            relationSearch.withFrom(topicService.lookUp(UUID.fromString(form.getFirstValue("topicId").trim())));
        }
    }

    private void loadTopics() {
        for (final Relation relation : relations) {
            topics.add(topicService.lookUp(relation.getToId()));
        }
    }

    private final TopicService topicService;
    private final RelationSearch relationSearch;
    private List<Relation> relations = Lists.newArrayList();
    private final List<Topic> topics = Lists.newArrayList();
}
