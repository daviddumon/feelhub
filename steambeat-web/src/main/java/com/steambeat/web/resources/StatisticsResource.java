package com.steambeat.web.resources;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.repositories.Repositories;
import com.steambeat.web.SteambeatTemplateRepresentation;
import org.joda.time.Interval;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.List;

public class StatisticsResource extends ServerResource {

    @Inject
    public StatisticsResource(final SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Get
    public Representation represent() throws JSONException {
        extractParameters(getQuery());
        fetchStatistics();
        return SteambeatTemplateRepresentation.createNew("json/statistics.json.ftl", getContext(), MediaType.APPLICATION_JSON).with("statistics", statistics);
    }

    private void fetchStatistics() {
        final Subject subject = Repositories.subjects().get(subjectId);
        statistics = Repositories.statistics().forSubject(subject, granularity, new Interval(start, end));
    }

    private void extractParameters(final Form query) {
        granularity = Granularity.valueOf(extract("granularity", query));
        start = Long.valueOf(extract("start", query));
        end = Long.valueOf(extract("end", query));
        subjectId = extract("subjectId", query);
    }

    private String extract(final String value, final Form query) {
        if (query.getQueryString().contains(value)) {
            return query.getFirstValue(value).trim();
        } else {
            throw new SteambeatJsonException();
        }
    }

    private List<Statistics> statistics = Lists.newArrayList();
    private SubjectService subjectService;
    private Granularity granularity;
    private Long start;
    private Long end;
    private String subjectId;
}
