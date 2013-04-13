package com.feelhub.web.resources;

import com.feelhub.application.TopicService;
import com.feelhub.domain.related.Related;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.*;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.*;
import com.feelhub.web.search.RelatedSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.Order;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class TopicResource extends ServerResource {

    @Inject
    public TopicResource(final TopicService topicService, final TopicDataFactory topicDataFactory, final ApiFeelingSearch apiFeelingSearch, final RelatedSearch relatedSearch) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
        this.apiFeelingSearch = apiFeelingSearch;
        this.relatedSearch = relatedSearch;
    }

    @Get
    public ModelAndView getTopic() {
        topic = topicService.lookUp(extractUriValueFromUri());
        if (checkCurrent(topic)) {
            final TopicData topicData = topicDataFactory.topicData(topic, CurrentUser.get().getLanguage());
            return getGoodTemplate(topicData);
        } else {
            setStatus(Status.REDIRECTION_PERMANENT);
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + topic.getCurrentId()));
            return ModelAndView.empty();
        }
    }

    private ModelAndView getGoodTemplate(final TopicData topicData) {
        final String templateName = getTemplateName();
        return ModelAndView.createNew(templateName)
                .with("topicData", topicData)
                .with("locales", FeelhubLanguage.availables())
                .with("relatedDatas", getRelatedDatas())
                .with("feelingDatas", getInitialFeelingDatas(templateName));
    }

    private String getTemplateName() {
        final Form query = getQuery();
        if (query != null && query.getFirstValue("_escaped_fragment_") != null) {
            return "topic-crawlable.ftl";
        }
        return "topic.ftl";
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

    private List<FeelingData> getInitialFeelingDatas(final String templateName) {
        final Form parameters = new Form();
        parameters.add("skip", "0");
        if (templateName.equalsIgnoreCase("topic.ftl")) {
            parameters.add("limit", "20");
        }
        return apiFeelingSearch.doSearch(topic, parameters);
    }

    private final TopicService topicService;
    private final TopicDataFactory topicDataFactory;
    private final ApiFeelingSearch apiFeelingSearch;
    private final RelatedSearch relatedSearch;
    private Topic topic;
}
