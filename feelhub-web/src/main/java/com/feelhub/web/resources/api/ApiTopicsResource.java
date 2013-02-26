package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.topic.CreateHttpTopicCommand;
import com.feelhub.application.command.topic.CreateRealTopicCommand;
import com.feelhub.domain.tag.TagNotFoundException;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.TopicIdentifier;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.UUID;

public class ApiTopicsResource extends ServerResource {

    @Inject
    public ApiTopicsResource(final TopicService topicService, final TopicDataFactory topicDataFactory, final CommandBus bus) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
        this.bus = bus;
    }

    @Get
    public ModelAndView getTopics() {
        List<TopicData> topicDatas = Lists.newArrayList();
        try {
            final String query = getQueryValue();
            final List<Topic> topics = topicService.getTopics(query, CurrentUser.get().getLanguage());
            setStatus(Status.SUCCESS_OK);
            topicDatas = getTopicDatas(topics);
        } catch (FeelhubApiException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (TagNotFoundException e) {
            setStatus(Status.SUCCESS_OK);
        }
        return ModelAndView.createNew("api/topics.json.ftl", MediaType.APPLICATION_JSON).with("topicDatas", topicDatas);
    }

    private List<TopicData> getTopicDatas(final List<Topic> topics) {
        final List<TopicData> results = Lists.newArrayList();
        for (final Topic topic : topics) {
            results.add(topicDataFactory.simpleTopicData(topic, CurrentUser.get().getLanguage()));
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
            if (TopicIdentifier.isHttpTopic(name)) {
                createHttpTopic(name);
            } else {
                createRealTopic(form, name);
            }
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

    private void createHttpTopic(final String name) {
        CreateHttpTopicCommand command = new CreateHttpTopicCommand(name, CurrentUser.get().getUser().getId());
        ListenableFuture<UUID> result = bus.execute(command);
        UUID topicId = Futures.getUnchecked(result);
        setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + topicId));
        setStatus(Status.SUCCESS_CREATED);
    }

    private void createRealTopic(final Form form, final String name) {
        final RealTopicType type = extractType(form);
        CreateRealTopicCommand command = new CreateRealTopicCommand(CurrentUser.get().getLanguage(), name, type, CurrentUser.get().getUser().getId());
        UUID topicId = Futures.getUnchecked(bus.execute(command));
        setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + topicId));
        setStatus(Status.SUCCESS_CREATED);
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
    private CommandBus bus;
}
