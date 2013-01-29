package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.media.Media;
import com.feelhub.domain.topic.*;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.MediaSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicMediasResource extends ServerResource {

    @Inject
    public ApiTopicMediasResource(final MediaSearch mediaSearch, final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.mediaSearch = mediaSearch;
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView getMediasTopics() {
        getTopic();
        doSearchWithQueryParameters();
        getTopicDataForEachMedia();
        return ModelAndView.createNew("api/medias.json.ftl", MediaType.APPLICATION_JSON).with("topicDataList", topicDataList);
    }

    private void getTopic() {
        try {
            final String topicId = getRequestAttributes().get("topicId").toString().trim();
            final Topic topic = topicService.lookUpCurrent(UUID.fromString(topicId));
            mediaSearch.withTopicId(topic.getCurrentId());
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private void doSearchWithQueryParameters() {
        final Form form = getQuery();
        setUpSearchForLimitParameter(form);
        setUpSearchForSkipParameter(form);
        medias = mediaSearch.execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubApiException();
            }
            mediaSearch.withLimit(limit);
        } else {
            mediaSearch.withLimit(100);
        }
    }

    private void setUpSearchForSkipParameter(final Form form) {
        if (form.getQueryString().contains("skip")) {
            mediaSearch.withSkip(Integer.parseInt(form.getFirstValue("skip").trim()));
        } else {
            mediaSearch.withSkip(0);
        }
    }

    private void getTopicDataForEachMedia() {
        for (final Media media : medias) {
            addTopicData(media);
        }
    }

    private void addTopicData(final Media media) {
        try {
            final Topic topic = topicService.lookUpCurrent(media.getToId());
            final TopicData topicData = topicDataFactory.simpleTopicData(topic, CurrentUser.get().getLanguage());
            topicDataList.add(topicData);
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private final MediaSearch mediaSearch;
    private final TopicService topicService;
    private final TopicDataFactory topicDataFactory;
    private List<Media> medias = Lists.newArrayList();
    private final List<TopicData> topicDataList = Lists.newArrayList();
}
