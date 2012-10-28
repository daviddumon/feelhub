package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.ReferenceService;
import com.steambeat.domain.reference.Reference;
import com.steambeat.domain.statistics.Granularity;
import com.steambeat.domain.statistics.Statistics;
import com.steambeat.web.representation.ModelAndView;
import com.steambeat.web.search.StatisticsSearch;
import org.joda.time.Interval;
import org.json.JSONException;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.UUID;

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
            throw new SteambeatJsonException();
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
