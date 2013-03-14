package com.feelhub.web.resources;

import com.feelhub.application.TopicService;
import com.feelhub.domain.media.Media;
import com.feelhub.domain.related.Related;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.*;
import com.feelhub.web.search.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.Order;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class TopicResource extends ServerResource {

    @Inject
    public TopicResource(final TopicService topicService, final TopicDataFactory topicDataFactory, final ApiFeelingSearch apiFeelingSearch, final RelatedSearch relatedSearch, final MediaSearch mediaSearch) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
        this.apiFeelingSearch = apiFeelingSearch;
        this.relatedSearch = relatedSearch;
        this.mediaSearch = mediaSearch;
    }

    @Get
    public ModelAndView getTopic() {
        topic = topicService.lookUp(extractUriValueFromUri());
        if (checkCurrent(topic)) {
            final TopicData topicData = topicDataFactory.topicData(topic, CurrentUser.get().getLanguage());
            return ModelAndView.createNew("topic.ftl")
                    .with("topicData", topicData)
                    .with("locales", FeelhubLanguage.availables())
                    .with("relatedDatas", getRelatedDatas())
                    .with("mediaDatas", getMediaDatas())
                    .with("feelingDatas", getInitialFeelingDatas());
        } else {
            setStatus(Status.REDIRECTION_PERMANENT);
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + topic.getCurrentId()));
            return ModelAndView.empty();
        }
    }

    private List<TopicData> getRelatedDatas() {
        relatedSearch.withTopicId(topic.getCurrentId());
        relatedSearch.withSkip(0);
        relatedSearch.withLimit(12);
        return buildTopicDataListFromRelated(relatedSearch.withSort("weight", Order.DESCENDING).execute());
    }

    private List<TopicData> buildTopicDataListFromRelated(final List<Related> relatedList) {
        final List<TopicData> results = Lists.newArrayList();
        for (final Related related : relatedList) {
            results.add(getTopicData(related.getToId()));
        }
        return results;
    }

    private List<TopicData> getMediaDatas() {
        mediaSearch.withTopicId(topic.getCurrentId());
        mediaSearch.withLimit(12);
        mediaSearch.withSkip(0);
        return buildTopicDataListFromMedia(mediaSearch.execute());
    }

    private List<TopicData> buildTopicDataListFromMedia(final List<Media> mediaList) {
        final List<TopicData> results = Lists.newArrayList();
        for (final Media media : mediaList) {
            results.add(getTopicData(media.getToId()));
        }
        return results;
    }

    private TopicData getTopicData(final UUID id) {
        try {
            final Topic topic = topicService.lookUpCurrent(id);
            return topicDataFactory.simpleTopicData(topic, CurrentUser.get().getLanguage());
        } catch (TopicNotFound e) {
            throw new FeelhubApiException();
        }
    }

    private UUID extractUriValueFromUri() {
        return UUID.fromString(getRequestAttributes().get("topicId").toString().trim());
    }

    private boolean checkCurrent(final Topic realTopic) {
        return realTopic.getId().equals(realTopic.getCurrentId());
    }

    private List<FeelingData> getInitialFeelingDatas() {
        final Form parameters = new Form();
        parameters.add("skip", "0");
        parameters.add("limit", "20");
        return apiFeelingSearch.doSearch(topic, parameters);
    }

    private final TopicService topicService;
    private final TopicDataFactory topicDataFactory;
    private final ApiFeelingSearch apiFeelingSearch;
    private final RelatedSearch relatedSearch;
    private final MediaSearch mediaSearch;
    private Topic topic;
}
