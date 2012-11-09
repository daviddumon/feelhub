package com.feelhub.web.resources.json;

import com.feelhub.application.*;
import com.feelhub.domain.feeling.*;
import com.feelhub.domain.keyword.Keyword;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.FeelingSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class JsonNewFeelingsResource extends ServerResource {

    @Inject
    public JsonNewFeelingsResource(final KeywordService keywordService, final TopicDataFactory topicDataFactory, final FeelingSearch feelingSearch, final TopicService topicService) {
        this.keywordService = keywordService;
        this.topicDataFactory = topicDataFactory;
        this.feelingSearch = feelingSearch;
        this.topicService = topicService;
    }

    @Get
    public ModelAndView represent() {
        final List<Feeling> feelings = doSearchWithQueryParameters(getQuery());
        final List<FeelingData> feelingDatas = getFeelingDatas(feelings);
        setStatus(Status.SUCCESS_OK);
        return ModelAndView.createNew("json/newfeelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
    }

    private List<Feeling> doSearchWithQueryParameters(final Form form) {
        final List<Feeling> feelings = Lists.newArrayList();
        setUpSearchForTopicIdParameter(form);
        getLastParameter(form);
        extractLanguageParameter(form);
        feelingSearch.withSkip(0);
        feelingSearch.withLimit(30);
        final List<Feeling> searchResult = feelingSearch.withSort("creationDate", FeelingSearch.REVERSE_ORDER).execute();
        int i = 0;
        boolean found = false;
        while (!found & i < searchResult.size()) {
            if (searchResult.get(i).getId().equals(lastFeelingId)) {
                found = true;
            } else {
                feelings.add(searchResult.get(i++));
                if (i % 30 == 0) {
                    feelingSearch.reset();
                    feelingSearch.withSkip(i).withLimit(30).withSort("creationDate", FeelingSearch.REVERSE_ORDER).withTopic(topic);
                    searchResult.addAll(feelingSearch.execute());
                }
            }
        }
        return feelings;
    }

    private void setUpSearchForTopicIdParameter(final Form form) {
        if (form.getQueryString().contains("topicId")) {
            topic = topicService.lookUp(UUID.fromString(form.getFirstValue("topicId").trim()));
            feelingSearch.withTopic(topic);
        }
    }

    private void getLastParameter(final Form form) {
        if (form.getQueryString().contains("lastFeelingId")) {
            lastFeelingId = UUID.fromString(form.getFirstValue("lastFeelingId").trim());
        }
    }

    private void extractLanguageParameter(final Form form) {
        if (form.getQueryString().contains("languageCode")) {
            feelhubLanguage = FeelhubLanguage.forString(form.getFirstValue("languageCode").trim());
        } else {
            feelhubLanguage = FeelhubLanguage.reference();
        }
    }

    private List<FeelingData> getFeelingDatas(final List<Feeling> feelings) {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            final List<TopicData> topicDatas = getTopicDatas(feeling);
            final FeelingData feelingData = new FeelingData(feeling, topicDatas);
            feelingDatas.add(feelingData);
        }
        return feelingDatas;
    }

    private List<TopicData> getTopicDatas(final Feeling feeling) {
        final List<TopicData> topicDatas = Lists.newArrayList();
        for (final Sentiment sentiment : feeling.getSentiments()) {
            final Keyword keyword = keywordService.lookUp(sentiment.getTopicId(), feelhubLanguage);
            final TopicData topicData = topicDataFactory.getTopicDatas(keyword, sentiment);
            topicDatas.add(topicData);
        }
        return topicDatas;
    }

    private final KeywordService keywordService;
    private final TopicDataFactory topicDataFactory;
    private final FeelingSearch feelingSearch;
    private final TopicService topicService;
    private FeelhubLanguage feelhubLanguage;
    private UUID lastFeelingId;
    private Topic topic;
}
