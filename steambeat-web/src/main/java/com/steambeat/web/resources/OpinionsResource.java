package com.steambeat.web.resources;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.*;
import com.steambeat.application.dto.JudgmentDTO;
import com.steambeat.domain.opinion.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.web.ReferenceBuilder;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.search.OpinionSearch;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class OpinionsResource extends ServerResource {

    @Inject
    public OpinionsResource(final SubjectService subjectService, final OpinionService opinionService, final OpinionSearch opinionSearch) {
        this.subjectService = subjectService;
        this.opinionService = opinionService;
        this.opinionSearch = opinionSearch;
    }

    @Get
    public SteambeatTemplateRepresentation represent() throws JSONException {
        doSearchWithQueryParameters();
        return SteambeatTemplateRepresentation.createNew("json/opinions.json.ftl", getContext(), MediaType.APPLICATION_JSON).with("opinions", opinions);
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
        if (form.getQueryString().contains("subjectId")) {
            opinionSearch.withSubject(subjectService.lookUpWebPage(UUID.fromString(form.getFirstValue("subjectId").trim())));
        }
    }

    @Post
    public void post(final Form form) {
        try {
            extractFormParameters(form);
        } catch (Exception e) {
            throw new OpinionCreationException();
        }
        final JudgmentDTO judgmentDTO = new JudgmentDTO(subject, feeling);
        opinionService.addOpinion(text, Lists.newArrayList(judgmentDTO));
        setStatus(Status.SUCCESS_CREATED);
        setLocationRef(new ReferenceBuilder(getContext()).buildUri("/webpages/" + subject.getId()));
    }

    private void extractFormParameters(final Form form) {
        if (form.getFirstValue("feeling") == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        }
        subject = subjectService.lookUpWebPage(UUID.fromString(form.getFirstValue("subjectId").trim()));
        feeling = Feeling.valueOf(form.getFirstValue("feeling").trim());
        text = form.getFirstValue("text").trim();
    }

    private Subject subject;
    private Feeling feeling;
    private String text;
    List<Opinion> opinions = Lists.newArrayList();
    private final OpinionSearch opinionSearch;
    private final SubjectService subjectService;
    private final OpinionService opinionService;
}
