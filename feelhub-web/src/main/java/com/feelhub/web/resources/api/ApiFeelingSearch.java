package com.feelhub.web.resources.api;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.user.User;
import com.feelhub.web.dto.*;
import com.feelhub.web.search.FeelingSearch;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.Order;
import org.restlet.data.Form;

import java.util.List;

public class ApiFeelingSearch {

    @Inject
    public ApiFeelingSearch(final FeelingSearch feelingSearch, final FeelingDataFactory feelingDataFactory) {
        this.feelingSearch = feelingSearch;
        this.feelingDataFactory = feelingDataFactory;
    }

    public List<FeelingData> doSearch(final Topic topic, final Form query) {
        feelingSearch.withTopicId(topic.getId());
        feelingSearch.ignoreEmptyFeelings();
        final List<Feeling> feelings = doSearchWithQueryParameters(query);
        return feelingDataFactory.feelingDatas(feelings);
    }

    public List<FeelingData> doSearch(final Form query, final User user) {
        feelingSearch.withUserId(user.getId());
        return doSearch(query);
    }

    public List<FeelingData> doSearch(final Form query) {
        feelingSearch.ignoreEmptyFeelings();
        final List<Feeling> feelings = doSearchWithQueryParameters(query);
        return feelingDataFactory.feelingDatas(feelings);
    }

    private List<Feeling> doSearchWithQueryParameters(final Form query) {
        setUpSearchForLimitParameter(query);
        setUpSearchForSkipParameter(query);
        return feelingSearch.withSort("creationDate", Order.DESCENDING).execute();
    }

    private void setUpSearchForLimitParameter(final Form form) {
        if (form.getQueryString().contains("limit")) {
            final int limit = Integer.parseInt(form.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubApiException("You can only set limit to a max value of 100");
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

    private final FeelingSearch feelingSearch;
    private final FeelingDataFactory feelingDataFactory;

}
