package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.relation.Relation;
import com.steambeat.domain.subject.Subject;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.search.*;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.*;

public class RelatedResource extends ServerResource {

    @Inject
    public RelatedResource(final SubjectService subjectService, final RelationSearch relationSearch) {
        this.subjectService = subjectService;
        this.relationSearch = relationSearch;
    }

    @Get
    public Representation represent() {
        doSearchWithQueryParameters();
        getSubjects();
        return SteambeatTemplateRepresentation.createNew("json/related.json.ftl", getContext(), MediaType.APPLICATION_JSON).with("subjects", subjects);
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
            relationSearch.withFrom(subjectService.lookUpSubject(UUID.fromString(form.getFirstValue("fromId").trim())));
        }
    }

    private void getSubjects() {
        for (Relation relation : relations) {
            subjects.add(subjectService.lookUpSubject(relation.getToId()));
        }
    }

    private SubjectService subjectService;
    private RelationSearch relationSearch;
    private List<Relation> relations = Lists.newArrayList();
    private List<Subject> subjects = Lists.newArrayList();
}
