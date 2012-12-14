package com.feelhub.domain.scraper;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.http.HttpTopic;
import com.google.common.collect.Lists;
import org.jsoup.nodes.Document;

import java.util.List;

public class EntityExtractor {

    public List<HttpTopic> analyze(final Document document, final FeelhubLanguage feelhubLanguage) {
        //final ArrayList<HttpTopic> httpTopics = Lists.newArrayList();
        //final String type = getField(document, "[property=og:type]");
        //if (!type.isEmpty()) {
        //    final HttpTopicType httpTopicType = getWebTopicType(type);
        //    final HttpTopic httpTopic = new HttpTopic(UUID.randomUUID(), httpTopicType);
        //    addName(document, feelhubLanguage, httpTopic);
        //    addDescription(document, feelhubLanguage, httpTopic);
        //    httpTopics.add(httpTopic);
        //}
        //return httpTopics;
        return Lists.newArrayList();
    }

    //private HttpTopicType getWebTopicType(final String typeValue) {
    //    try {
    //        return HttpTopicType.valueOf(WordUtils.capitalizeFully(typeValue));
    //    } catch (IllegalArgumentException e) {
    //        return HttpTopicType.Other;
    //    }
    //}
    //
    //private void addName(final Document document, final FeelhubLanguage feelhubLanguage, final HttpTopic httpTopic) {
    //    final String title = getField(document, "[property=og:title]");
    //    if (!title.isEmpty()) {
    //        httpTopic.addName(feelhubLanguage, title);
    //    }
    //}
    //
    //private void addDescription(final Document document, final FeelhubLanguage feelhubLanguage, final HttpTopic httpTopic) {
    //    final String description = getField(document, "[property=og:description]");
    //    if (!description.isEmpty()) {
    //        httpTopic.addDescription(feelhubLanguage, description);
    //    }
    //}
    //
    //private String getField(final Document document, final String field) {
    //    final Element titleElement = document.select(field).first();
    //    if (titleElement != null) {
    //        return titleElement.attr("content");
    //    } else {
    //        return "";
    //    }
    //}
}
