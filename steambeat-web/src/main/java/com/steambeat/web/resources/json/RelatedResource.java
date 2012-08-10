package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.TopicService;
import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.topic.Topic;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.search.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.*;

public class RelatedResource extends ServerResource {

    @Inject
    public RelatedResource(final TopicService topicService, final RelationSearch relationSearch) {
        this.topicService = topicService;
        this.relationSearch = relationSearch;
    }

    @Get
    public Representation represent() {
        doSearchWithQueryParameters();
        getTopics();
        return SteambeatTemplateRepresentation.createNew("json/related.json.ftl", getContext(), MediaType.APPLICATION_JSON).with("topics", topics);
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
                throw new SteambeatJsonException();
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
        if (form.getQueryString().contains("fromId")) {
            relationSearch.withFrom(topicService.lookUp(UUID.fromString(form.getFirstValue("fromId").trim())));
        }
    }

    private void getTopics() {
        for (final Relation relation : relations) {
            topics.add(topicService.lookUp(relation.getToId()));
        }
    }

    private final TopicService topicService;
    private final RelationSearch relationSearch;
    private List<Relation> relations = Lists.newArrayList();
    private final List<Topic> topics = Lists.newArrayList();
}
