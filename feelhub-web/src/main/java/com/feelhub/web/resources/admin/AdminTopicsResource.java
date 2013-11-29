package com.feelhub.web.resources.admin;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.http.HttpTopic;
import com.feelhub.domain.topic.real.RealTopic;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;

public class AdminTopicsResource extends ServerResource {

    @Inject
    public AdminTopicsResource(TopicDataFactory topicDataFactory) {
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("admin/topics.ftl").with("topics", getTopicsDatas());
    }

    private List<TopicData> getTopicsDatas() {
        return Lists.newArrayList(Iterables.transform(getTopics(), new Function<Topic, TopicData>() {
            @Override
            public TopicData apply(Topic input) {
                return topicDataFactory.simpleTopicData(input, FeelhubLanguage.REFERENCE);
            }
        }));
    }

    private List<Topic> getTopics() {
        return Lists.newArrayList(Iterables.filter(Repositories.topics().findWithoutThumbnail(), new Predicate<Topic>() {
            @Override
            public boolean apply(Topic topic) {
                return topic instanceof HttpTopic || topic instanceof RealTopic;
            }
        }));
    }

    private TopicDataFactory topicDataFactory;
}
