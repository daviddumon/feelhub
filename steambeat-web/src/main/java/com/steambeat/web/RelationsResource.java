package com.steambeat.web;

import com.google.inject.Inject;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.analytics.Relation;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.resources.SteambeatJsonException;
import com.steambeat.web.search.RelationSearch;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.*;

public class RelationsResource extends ServerResource {

    @Inject
    public RelationsResource(final SubjectService subjectService, final RelationSearch relationSearch) {
        this.subjectService = subjectService;
        this.relationSearch = relationSearch;
    }

    @Get
    public Representation represent() {
        doSearchWithQueryParameters();
        return SteambeatTemplateRepresentation.createNew("json/relations.json.ftl", getContext(), MediaType.APPLICATION_JSON).with("relations", relations);
    }

    private void doSearchWithQueryParameters() {
        final Form form = getQuery();
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        setUpSearchForFromIdParameter(form);
        relations = relationSearch.execute();
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
            relationSearch.withFrom(subjectService.lookUpSubject(UUID.fromString(form.getFirstValue("fromId").trim())));
        }
    }

    private SubjectService subjectService;
    private RelationSearch relationSearch;
    private List<Relation> relations;
}
