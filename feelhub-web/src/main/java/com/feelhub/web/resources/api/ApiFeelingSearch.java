package com.feelhub.web.resources.api;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.search.FeelingSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.Form;

import java.util.List;

public class ApiFeelingSearch {

    @Inject
    public ApiFeelingSearch(final FeelingSearch feelingSearch, final TopicDataFactory topicDataFactory) {
        this.feelingSearch = feelingSearch;
        this.topicDataFactory = topicDataFactory;
    }

    public List<FeelingData> doSearch(final Topic topic, final Form query) {
        feelingSearch.withTopicId(topic.getId());
        return doSearch(query);
    }

    public List<FeelingData> doSearch(final Form query) {
        final List<Feeling> feelings = doSearchWithQueryParameters(query);
        final List<FeelingData> feelingDatas = getFeelingDatas(feelings);
        return feelingDatas;
    }

    private List<Feeling> doSearchWithQueryParameters(final Form query) {
        setUpSearchForLimitParameter(query);
        setUpSearchForSkipParameter(query);
        return feelingSearch.withSort("creationDate", FeelingSearch.REVERSE_ORDER).execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubApiException();
            }
            feelingSearch.withLimit(limit);
        } else {
            feelingSearch.withLimit(100);
        }
    }

    private void setUpSearchForSkipParameter(final Form form) {
        if (form.getQueryString().contains("skip")) {
            feelingSearch.withSkip(Integer.parseInt(form.getFirstValue("skip").trim()));
        } else {
            feelingSearch.withSkip(0);
        }
    }

    private List<FeelingData> getFeelingDatas(final List<Feeling> feelings) {
        final List<FeelingData> feelingDatas = Lists.newArrayList();
        for (final Feeling feeling : feelings) {
            final List<TopicData> topicDatas = topicDataFactory.getTopicDatas(feeling, CurrentUser.get().getLanguage());
            final FeelingData feelingData = new FeelingData(feeling, topicDatas);
            feelingDatas.add(feelingData);
        }
        return feelingDatas;
    }

    private final FeelingSearch feelingSearch;
    private final TopicDataFactory topicDataFactory;
}
