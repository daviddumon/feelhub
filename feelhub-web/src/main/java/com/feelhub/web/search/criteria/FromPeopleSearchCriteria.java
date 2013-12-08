package com.feelhub.web.search.criteria;

import com.feelhub.web.search.TopicSearch;

import java.util.UUID;

public enum FromPeopleSearchCriteria {
    All("From everyone") {
        @Override
        public TopicSearch addCriteria(UUID userId, TopicSearch topicSearch) {
            return topicSearch;
        }
    }, My("From me") {
        @Override
        public TopicSearch addCriteria(UUID userId, TopicSearch topicSearch) {
            if (userId == null) {
                return All.addCriteria(userId, topicSearch);
            }
            return topicSearch.withUserId(userId);
        }
    };

    FromPeopleSearchCriteria(String stringValue) {
        this.stringValue = stringValue;
    }

    static FromPeopleSearchCriteria defaultValue() {
        return All;
    }

    public static FromPeopleSearchCriteria fromString(String value) {
        for (FromPeopleSearchCriteria criteria : values()) {
            if ((criteria.stringValue).equals(value)) {
                return criteria;
            }
        }
        return defaultValue();
    }

    public abstract TopicSearch addCriteria(UUID userId, TopicSearch topicSearch);

    private String stringValue;
}
