package com.feelhub.web.resources;

import com.feelhub.application.TopicService;
import com.feelhub.domain.topic.*;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.util.List;

public class SearchResource extends ServerResource {

    @Inject
    public SearchResource(final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView search() {
        final String query;
        try {
            query = getSearchQuery();
            setStatus(Status.SUCCESS_OK);
            final List<Topic> topics = topicService.getTopics(query, CurrentUser.get().getLanguage());
            if (TopicIdentifier.isHttpTopic(query)) {
                return ModelAndView.createNew("search.ftl").with("q", query).with("type", "http").with("topicDatas", getTopicDatas(topics));
            } else {
                return ModelAndView.createNew("search.ftl").with("q", query).with("type", "real").with("topicDatas", getTopicDatas(topics));
            }
        } catch (Exception e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("error.ftl");
        }
    }

    private String getSearchQuery() throws Exception {
        if (getQuery().getQueryString().contains("q")) {
            return getQuery().getFirstValue("q");
        } else {
            throw new Exception();
        }
    }

    private List<TopicData> getTopicDatas(final List<Topic> topics) {
        final List<TopicData> results = Lists.newArrayList();
        for (final Topic topic : topics) {
            results.add(topicDataFactory.simpleTopicData(topic, CurrentUser.get().getLanguage()));
        }
        return results;
    }

    private TopicService topicService;
    private TopicDataFactory topicDataFactory;
}
