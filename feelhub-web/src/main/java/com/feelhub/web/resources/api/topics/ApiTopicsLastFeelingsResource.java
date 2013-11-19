package com.feelhub.web.resources.api.topics;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.feelhub.web.search.TopicSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.Order;
import org.restlet.data.Form;
import org.restlet.resource.*;

import java.util.List;

public class ApiTopicsLastFeelingsResource extends ServerResource {

    @Inject
    public ApiTopicsLastFeelingsResource(final TopicSearch topicSearch, final TopicDataFactory topicDataFactory) {
        this.topicSearch = topicSearch;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView represent() {
        int limit = 100;
        int skip = 0;
        final Form query = getQuery();
        if (query != null && query.getQueryString().contains("limit")) {
            limit = Integer.parseInt(query.getFirstValue("limit").trim());
            if (limit > 100) {
                throw new FeelhubApiException();
            }
        }
        if (query != null && query.getQueryString().contains("skip")) {
            skip = Integer.parseInt(query.getFirstValue("skip").trim());
        }
        return ModelAndView.createNew("api/topics.json.ftl").with("topicDatas", getTopicDatas(skip, limit));
    }

    public List<TopicData> getTopicDatas(final int skip, final int limit) {
        final List<FeelhubLanguage> languages = Lists.newArrayList();
        languages.add(FeelhubLanguage.reference());
        languages.add(FeelhubLanguage.none());
        if (!CurrentUser.get().getLanguage().isReference() && !CurrentUser.get().getLanguage().isNone()) {
            languages.add(CurrentUser.get().getLanguage());
        }

        final List<Topic> topics = topicSearch
                .withFeelings()
                .withSort("lastModificationDate", Order.DESCENDING)
                .withLimit(limit)
                .withSkip(skip)
                .withLanguages(languages)
                .execute();

        final List<TopicData> topicDatas = Lists.newArrayList();
        for (final Topic topic : topics) {
            topicDatas.add(topicDataFactory.topicData(topic, CurrentUser.get().getLanguage()));
        }
        return topicDatas;
    }

    private final TopicSearch topicSearch;
    private final TopicDataFactory topicDataFactory;
}
