package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.web.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang.WordUtils;
import org.jsoup.nodes.*;

import java.util.*;

public class EntityExtractor {

    public List<WebTopic> analyze(final Document document, final FeelhubLanguage feelhubLanguage) {
        final ArrayList<WebTopic> webTopics = Lists.newArrayList();
        final String type = getField(document, "[property=og:type]");
        if (!type.isEmpty()) {
            final WebTopicType webTopicType = getWebTopicType(type);
            final WebTopic webTopic = new WebTopic(UUID.randomUUID(), webTopicType);
            addName(document, feelhubLanguage, webTopic);
            addDescription(document, feelhubLanguage, webTopic);
            webTopics.add(webTopic);
        }
        return webTopics;
    }

    private WebTopicType getWebTopicType(final String typeValue) {
        try {
            return WebTopicType.valueOf(WordUtils.capitalizeFully(typeValue));
        } catch (IllegalArgumentException e) {
            return WebTopicType.Other;
        }
    }

    private void addName(final Document document, final FeelhubLanguage feelhubLanguage, final WebTopic webTopic) {
        final String title = getField(document, "[property=og:title]");
        if (!title.isEmpty()) {
            webTopic.addName(feelhubLanguage, title);
        }
    }

    private void addDescription(final Document document, final FeelhubLanguage feelhubLanguage, final WebTopic webTopic) {
        final String description = getField(document, "[property=og:description]");
        if (!description.isEmpty()) {
            webTopic.addDescription(feelhubLanguage, description);
        }
    }

    private String getField(final Document document, final String field) {
        final Element titleElement = document.select(field).first();
        if (titleElement != null) {
            return titleElement.attr("content");
        } else {
            return "";
        }
    }
}
