package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.ReferenceService;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.search.OpinionSearch;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class JsonOpinionsResource extends ServerResource {

    @Inject
    public JsonOpinionsResource(final ReferenceService referenceService, final OpinionSearch opinionSearch) {
        this.referenceService = referenceService;
        this.opinionSearch = opinionSearch;
    }

    @Get
    public SteambeatTemplateRepresentation represent() throws JSONException {
        doSearchWithQueryParameters();
        return SteambeatTemplateRepresentation.createNew("json/opinions.json.ftl", getContext(), MediaType.APPLICATION_JSON, getRequest()).with("opinions", opinions);
    }

    private void doSearchWithQueryParameters() {
        final Form form = getQuery();
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        setUpSearchForSubjectIdParameter(form);
        opinions = opinionSearch.withSort("creationDate", OpinionSearch.REVERSE_ORDER).execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new SteambeatJsonException();
            }
            opinionSearch.withLimit(limit);
        } else {
            opinionSearch.withLimit(100);
        }
    }

    private void setUpSearchForSkipParameter(final Form form) {
        if (form.getQueryString().contains("skip")) {
            opinionSearch.withSkip(Integer.parseInt(form.getFirstValue("skip").trim()));
        } else {
            opinionSearch.withSkip(0);
        }
    }

    private void setUpSearchForSubjectIdParameter(final Form form) {
        if (form.getQueryString().contains("topicId")) {
            opinionSearch.withTopic(referenceService.lookUp(UUID.fromString(form.getFirstValue("topicId").trim())));
        }
    }

    List<Opinion> opinions = Lists.newArrayList();
    private final OpinionSearch opinionSearch;
    private final ReferenceService referenceService;
}
