package com.feelhub.web.resources.json;

import com.feelhub.application.ReferenceService;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.relation.Relation;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class JsonRelationsResource extends ServerResource {

    @Inject
    public JsonRelationsResource(final ReferenceService referenceService, final RelationSearch relationSearch) {
        this.referenceService = referenceService;
        this.relationSearch = relationSearch;
    }

    @Get
    public ModelAndView represent() {
        doSearchWithQueryParameters();
        loadReferences();
        return ModelAndView.createNew("json/relations.json.ftl", MediaType.APPLICATION_JSON).with("references", references);
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
                throw new FeelhubJsonException();
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
        if (form.getQueryString().contains("referenceId")) {
            relationSearch.withFrom(referenceService.lookUp(UUID.fromString(form.getFirstValue("referenceId").trim())));
        }
    }

    private void loadReferences() {
        for (final Relation relation : relations) {
            references.add(referenceService.lookUp(relation.getToId()));
        }
    }

    private final ReferenceService referenceService;
    private final RelationSearch relationSearch;
    private List<Relation> relations = Lists.newArrayList();
    private final List<Reference> references = Lists.newArrayList();
}
