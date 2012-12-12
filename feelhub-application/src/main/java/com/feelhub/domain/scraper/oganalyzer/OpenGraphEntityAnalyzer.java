package com.feelhub.domain.scraper.oganalyzer;

import com.feelhub.domain.topic.usable.web.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang.WordUtils;
import org.jsoup.nodes.*;

import java.util.*;

public class OpenGraphEntityAnalyzer {

    public List<WebTopic> analyze(final Document document) {
        final ArrayList<WebTopic> webTopics = Lists.newArrayList();
        final String type = getType(document);
        if (!type.isEmpty()) {
            final WebTopicType webTopicType = getWebTopicType(type);
            final WebTopic webTopic = new WebTopic(UUID.randomUUID(), webTopicType);


            webTopics.add(webTopic);
        }
        return webTopics;
    }

    private String getType(final Document document) {
        final Element typeElement = document.select("[property=og:type]").first();
        if (typeElement != null) {
            final String typeValue = typeElement.attr("content");
            return typeValue;
        } else {
            return "";
        }
    }

    private WebTopicType getWebTopicType(final String typeValue) {
        try {
            return WebTopicType.valueOf(WordUtils.capitalizeFully(typeValue));
        } catch (IllegalArgumentException e) {
            return WebTopicType.Other;
        }
    }
}
