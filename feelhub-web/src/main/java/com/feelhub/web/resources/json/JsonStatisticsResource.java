package com.feelhub.web.resources.json;

import com.feelhub.application.ReferenceService;
import com.feelhub.domain.reference.Reference;
import com.feelhub.domain.statistics.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.StatisticsSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.joda.time.Interval;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class JsonStatisticsResource extends ServerResource {

    @Inject
    public JsonStatisticsResource(final ReferenceService referenceService, final StatisticsSearch statisticsSearch) {
        this.referenceService = referenceService;
        this.statisticsSearch = statisticsSearch;
    }

    @Get
    public ModelAndView represent() throws JSONException {
        extractParameters(getQuery());
        fetchStatistics();
        return ModelAndView.createNew("json/statistics.json.ftl", MediaType.APPLICATION_JSON).with("statistics", statistics);
    }

    private void extractParameters(final Form query) {
        granularity = Granularity.valueOf(extract("granularity", query));
        start = Long.valueOf(extract("start", query));
        end = Long.valueOf(extract("end", query));
        referenceId = extract("referenceId", query);
    }

    private String extract(final String value, final Form query) {
        if (query.getQueryString().contains(value)) {
            return query.getFirstValue(value).trim();
        } else {
            throw new FeelhubJsonException();
        }
    }

    private void fetchStatistics() {
        final Reference reference = referenceService.lookUp(UUID.fromString(referenceId));
        statistics = statisticsSearch.withReference(reference).withGranularity(granularity).withInterval(new Interval(start, end)).execute();
    }

    private List<Statistics> statistics = Lists.newArrayList();
    private final ReferenceService referenceService;
    private final StatisticsSearch statisticsSearch;
    private Granularity granularity;
    private Long start;
    private Long end;
    private String referenceId;
}
