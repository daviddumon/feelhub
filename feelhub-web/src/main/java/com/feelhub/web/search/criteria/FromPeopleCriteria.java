package com.feelhub.web.search.criteria;

import com.feelhub.web.search.TopicSearch;

public enum FromPeopleCriteria {
    All {
	@Override
	public TopicSearch addCriteria(TopicSearch topicSearch) {
	    return topicSearch;
	}
    }, My {
	@Override
	public TopicSearch addCriteria(TopicSearch topicSearch) {
	    return topicSearch;
	}
    };

    static FromPeopleCriteria defaultValue() {
	return All;
    }

    public static FromPeopleCriteria fromString(String value) {
	for (FromPeopleCriteria criteria : values()) {
	    if ((criteria.name() + " topics").equals(value)) {
		return criteria;
	    }
	}
	return defaultValue();
    }

    public abstract TopicSearch addCriteria(TopicSearch topicSearch);
}
