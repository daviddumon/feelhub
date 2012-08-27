package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.ReferenceService;
import com.steambeat.domain.relation.Relation;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.search.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.*;

public class RelatedResource extends ServerResource {

    @Inject
    public RelatedResource(final ReferenceService referenceService, final RelationSearch relationSearch) {
        this.referenceService = referenceService;
        this.relationSearch = relationSearch;
    }

    @Get
    public Representation represent() {
        doSearchWithQueryParameters();
        getTopics();
        return SteambeatTemplateRepresentation.createNew("json/related.json.ftl", getContext(), MediaType.APPLICATION_JSON, getRequest()).with("topics", references);
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
            relationSearch.withFrom(referenceService.lookUp(UUID.fromString(form.getFirstValue("fromId").trim())));
        }
    }

    private void getTopics() {
        for (final Relation relation : relations) {
            references.add(referenceService.lookUp(relation.getToId()));
        }
    }

    private final ReferenceService referenceService;
    private final RelationSearch relationSearch;
    private List<Relation> relations = Lists.newArrayList();
    private final List<com.steambeat.domain.reference.Reference> references = Lists.newArrayList();
}
