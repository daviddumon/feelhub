package com.feelhub.web.resources;

import com.feelhub.application.*;
import com.feelhub.domain.tag.Tag;
import com.feelhub.domain.topic.TopicNotFound;
import com.feelhub.domain.topic.usable.UsableTopic;
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
    public SearchResource(final TopicService topicService, final TagService tagService, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.tagService = tagService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView search() {
        final String query = getRequestAttributes().get("q").toString().trim();
        final List<TopicData> topicDatas = Lists.newArrayList();
        try {
            final Tag tag = tagService.lookUp(query);
            final List<UsableTopic> realTopics = getTopics(tag);
            topicDatas.addAll(getTopicDatas(realTopics));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStatus(Status.SUCCESS_OK);
        return ModelAndView.createNew("search.ftl").with("q", query).with("topicDatas", topicDatas);
    }

    private List<UsableTopic> getTopics(final com.feelhub.domain.tag.Tag tag) {
        final List<UsableTopic> realTopics = Lists.newArrayList();
        for (final UUID id : tag.getTopicIds()) {
            try {
                realTopics.add((UsableTopic) topicService.lookUp(id));
            } catch (TopicNotFound e) {
                e.printStackTrace();
            }
        }
        return realTopics;
    }

    private List<TopicData> getTopicDatas(final List<UsableTopic> realTopics) {
        final List<TopicData> result = Lists.newArrayList();
        for (final UsableTopic realTopic : realTopics) {
            result.add(topicDataFactory.getTopicData(realTopic, CurrentUser.get().getLanguage()));
        }
        return result;
    }

    private final TopicService topicService;
    private final TagService tagService;
    private final TopicDataFactory topicDataFactory;
}
