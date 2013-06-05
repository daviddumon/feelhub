package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.statistics.*;
import com.feelhub.domain.topic.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.StatisticsSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.joda.time.Interval;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicStatisticsResource extends ServerResource {

    @Inject
    public ApiTopicStatisticsResource(final TopicService topicService, final StatisticsSearch statisticsSearch) {
        this.topicService = topicService;
        this.statisticsSearch = statisticsSearch;
    }

    @Get
    public ModelAndView represent() throws JSONException {
        try {
            extractRequestAttributes();
            extractParameters(getQuery());
            fetchStatistics();
            return ModelAndView.createNew("api/statistics.json.ftl", MediaType.APPLICATION_JSON).with("statistics", statistics);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        } catch (TopicNotFound e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/api-error.ftl").with("exception", e);
        }
    }

    private void extractRequestAttributes() {
        topicId = getRequestAttributes().get("topicId").toString();
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
        final Topic realTopic = topicService.lookUpCurrent(UUID.fromString(topicId));
        statistics = statisticsSearch.withTopicId(realTopic.getId()).withGranularity(granularity).withInterval(new Interval(start, end)).execute();
    }

    private List<Statistics> statistics = Lists.newArrayList();
    private final StatisticsSearch statisticsSearch;
    private Granularity granularity;
    private Long start;
    private Long end;
    private String topicId;
    private final TopicService topicService;
}
