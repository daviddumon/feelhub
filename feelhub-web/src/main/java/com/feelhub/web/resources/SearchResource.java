package com.feelhub.web.resources;

import com.feelhub.application.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.*;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.util.*;

public class SearchResource extends ServerResource {

    @Inject
    public SearchResource(final TopicService topicService, TagService tagService, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.tagService = tagService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView search() {
        final String query = getRequestAttributes().get("q").toString().trim();
        List<TopicData> topicDatas = Lists.newArrayList();
        try {
            final Tag tag = tagService.lookUp(query);
            List<Topic> topics = getTopics(tag);
            topicDatas.addAll(getTopicDatas(topics));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStatus(Status.SUCCESS_OK);
        return ModelAndView.createNew("search.ftl").with("q", query).with("topicDatas", topicDatas);
    }

    private List<Topic> getTopics(final com.feelhub.domain.tag.Tag tag) {
        List<Topic> topics = Lists.newArrayList();
        for (UUID id : tag.getTopicIds()) {
            try {
                topics.add(topicService.lookUp(id));
            } catch (TopicNotFound e) {
                e.printStackTrace();
            }
        }
        return topics;
    }

    private List<TopicData> getTopicDatas(final List<Topic> topics) {
        List<TopicData> result = Lists.newArrayList();
        for (Topic topic : topics) {
            result.add(topicDataFactory.getTopicData(topic, CurrentUser.get().getLanguage()));
        }
        return result;
    }

    private TopicService topicService;
    private final TagService tagService;
    private final TopicDataFactory topicDataFactory;
}
