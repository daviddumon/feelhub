package com.feelhub.web.resources.api.topics;

import com.feelhub.domain.feeling.Feeling;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.*;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.mongolink.domain.criteria.Order;
import org.restlet.resource.*;

import java.util.List;

public class ApiTopicsLastFeelingsResource extends ServerResource {

    @Inject
    public ApiTopicsLastFeelingsResource(final TopicSearch topicSearch, final FeelingSearch feelingSearch, final TopicDataFactory topicDataFactory) {
        this.topicSearch = topicSearch;
        this.feelingSearch = feelingSearch;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("api/topics.json.ftl").with("topicDatas", getTopicDatas(0, 50));
    }

    public List<TopicData> getTopicDatas(final int skip, final int limit) {
        final List<Feeling> feelings = feelingSearch.withLimit(50).withSkip(0).withSort("creationDate", Order.DESCENDING).execute();
        final List<Topic> topics = topicSearch.forFeelings(feelings).withSort("lastModificationDate", Order.DESCENDING).execute();
        final List<TopicData> topicDatas = Lists.newArrayList();
        for (final Topic topic : topics) {
            topicDatas.add(topicDataFactory.topicData(topic, CurrentUser.get().getLanguage()));
        }
        return topicDatas;
    }

    private final TopicSearch topicSearch;
    private final FeelingSearch feelingSearch;
    private final TopicDataFactory topicDataFactory;
}
