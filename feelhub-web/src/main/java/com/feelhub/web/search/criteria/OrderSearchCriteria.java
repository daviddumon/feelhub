package com.feelhub.web.search.criteria;

import com.feelhub.web.search.TopicSearch;
import org.mongolink.domain.criteria.Order;

public enum OrderSearchCriteria {
    Hot {
	@Override
	public TopicSearch addCriteria(TopicSearch topicSearch) {
	    return topicSearch.withFeelings().withSort("lastModificationDate", Order.DESCENDING);
	}
    }, Popular {
	@Override
	public TopicSearch addCriteria(TopicSearch topicSearch) {
	    return topicSearch.withFeelings().withSort("popularityCount", Order.DESCENDING);
	}
    }, New {
	@Override
	public TopicSearch addCriteria(TopicSearch topicSearch) {
	    return topicSearch.withSort("creationDate", Order.DESCENDING);
	}
    };

    public abstract TopicSearch addCriteria(TopicSearch topicSearch);

    static OrderSearchCriteria defaultValue() {
	return Hot;
    }

    public static OrderSearchCriteria fromString(String value) {
	for (OrderSearchCriteria orderSearchCriteria : values()) {
	    if ((orderSearchCriteria.name() + " topics").equals(value)) {
		return orderSearchCriteria;
	    }
	}
	return defaultValue();
    }
}
