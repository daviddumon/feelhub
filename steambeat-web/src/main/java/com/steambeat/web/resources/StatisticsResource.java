package com.steambeat.web.resources;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.SubjectService;
import com.steambeat.domain.statistics.*;
import com.steambeat.domain.subject.Subject;
import com.steambeat.web.SteambeatTemplateRepresentation;
import com.steambeat.web.search.StatisticsSearch;
import org.joda.time.Interval;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import java.util.*;

public class StatisticsResource extends ServerResource {

    @Inject
    public StatisticsResource(final SubjectService subjectService, final StatisticsSearch statisticsSearch) {
        this.subjectService = subjectService;
        this.statisticsSearch = statisticsSearch;
    }

    @Get
    public Representation represent() throws JSONException {
        extractParameters(getQuery());
        fetchStatistics();
        return SteambeatTemplateRepresentation.createNew("json/statistics.json.ftl", getContext(), MediaType.APPLICATION_JSON).with("statistics", statistics);
    }

    private void fetchStatistics() {
        final Subject subject = subjectService.lookUpWebPage(UUID.fromString(subjectId));
        statistics = statisticsSearch.withSubject(subject).withGranularity(granularity).withInterval(new Interval(start, end)).execute();
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
    private final SubjectService subjectService;
    private final StatisticsSearch statisticsSearch;
    private Granularity granularity;
    private Long start;
    private Long end;
    private String subjectId;
}
