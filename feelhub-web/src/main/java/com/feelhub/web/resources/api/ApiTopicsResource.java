package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.tag.TagNotFoundException;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.*;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.List;

public class ApiTopicsResource extends ServerResource {

    @Inject
    public ApiTopicsResource(final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView getTopics() {
        try {
            final String query = getQueryValue();
            final List<Topic> topics = topicService.getTopics(query);
            return ModelAndView.createNew("api/topics.json.ftl", MediaType.APPLICATION_JSON).with("topicDatas", getTopicDatas(topics));
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return ModelAndView.createNew("api/topics.json.ftl", MediaType.APPLICATION_JSON).with("topicDatas", Lists.newArrayList());
        } catch (TagNotFoundException e) {
            setStatus(Status.SUCCESS_OK);
            return ModelAndView.createNew("api/topics.json.ftl", MediaType.APPLICATION_JSON).with("topicDatas", Lists.newArrayList());
        }
    }

    private List<TopicData> getTopicDatas(final List<Topic> topics) {
        List<TopicData> results = Lists.newArrayList();
        for (Topic topic : topics) {
            results.add(topicDataFactory.getTopicData(topic, CurrentUser.get().getLanguage()));
        }
        return results;
    }

    public String getQueryValue() {
        if (getQuery().getQueryString().contains("q")) {
            return getQuery().getFirstValue("q").toString();
        } else {
            throw new FeelhubApiException();
        }
    }

    @Post
    public void createTopic(final Form form) {
        try {
            checkCredentials();
            final String name = extractName(form);
            final RealTopicType type = extractType(form);
            final RealTopic realTopic = topicService.createRealTopic(CurrentUser.get().getLanguage(), name, type, CurrentUser.get().getUser());
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + realTopic.getId()));
            setStatus(Status.SUCCESS_CREATED);
        } catch (AuthenticationException e) {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }

    private String extractName(final Form form) {
        if (form.getQueryString().contains("name")) {
            return form.getFirstValue("name");
        } else {
            throw new FeelhubApiException();
        }
    }

    private RealTopicType extractType(final Form form) {
        if (form.getQueryString().contains("type")) {
            return RealTopicType.valueOf(form.getFirstValue("type"));
        } else {
            throw new FeelhubApiException();
        }
    }

    private final TopicService topicService;
    private TopicDataFactory topicDataFactory;
}
