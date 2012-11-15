package com.feelhub.web.resources.api;

import com.feelhub.application.UriService;
import com.feelhub.domain.keyword.uri.*;
import com.feelhub.domain.statistics.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.StatisticsSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.joda.time.Interval;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.List;

public class ApiUriStatisticsResource extends ServerResource {

    @Inject
    public ApiUriStatisticsResource(final UriService uriService, final StatisticsSearch statisticsSearch) {
        this.uriService = uriService;
        this.statisticsSearch = statisticsSearch;
    }

    @Get
    public ModelAndView represent() throws JSONException {
        extractRequestAttributes();
        extractParameters(getQuery());
        fetchStatistics();
        return ModelAndView.createNew("api/statistics.json.ftl", MediaType.APPLICATION_JSON).with("statistics", statistics);
    }

    private void extractRequestAttributes() {
        id = getRequestAttributes().get("id").toString();
    }

    private void extractParameters(final Form query) {
        granularity = Granularity.valueOf(extract("granularity", query));
        start = Long.valueOf(extract("start", query));
        end = Long.valueOf(extract("end", query));
    }

    private String extract(final String value, final Form query) {
        if (query.getQueryString().contains(value)) {
            return query.getFirstValue(value).trim();
        } else {
            throw new FeelhubApiException();
        }
    }

    private void fetchStatistics() {
        try {
            final Uri uri = uriService.getUri(id);
            statistics = statisticsSearch.withTopicId(uri.getTopicId()).withGranularity(granularity).withInterval(new Interval(start, end)).execute();
        } catch (UriNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private List<Statistics> statistics = Lists.newArrayList();
    private final UriService uriService;
    private final StatisticsSearch statisticsSearch;
    private Granularity granularity;
    private Long start;
    private Long end;
    private String id;
}
