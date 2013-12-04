package com.feelhub.web.resources.api.topics;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.topic.SearchOrCreateTopicCommand;
import com.feelhub.application.search.TopicSearch;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.FeelhubApiException;
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
    public ApiTopicsResource(final TopicSearch topicSearch, final TopicDataFactory topicDataFactory, final CommandBus bus) {
        this.topicSearch = topicSearch;
        this.topicDataFactory = topicDataFactory;
        this.bus = bus;
    }

    @Get
    public ModelAndView getTopics() {
        final String query = getQueryValue();
        final List<Topic> topics = topicSearch.findTopics(query, CurrentUser.get().getLanguage());
        setStatus(Status.SUCCESS_OK);
        return ModelAndView.createNew("api/topics.json.ftl", MediaType.APPLICATION_JSON).with("topicDatas", getTopicDatas(topics));
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
            SearchOrCreateTopicCommand command = new SearchOrCreateTopicCommand(extractName(form), CurrentUser.get().getUser().getId());
            ListenableFuture<UUID> future = bus.execute(command);
            UUID id = Futures.getUnchecked(future);
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + id));
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

    private final CommandBus bus;
    private final TopicSearch topicSearch;
    private final TopicDataFactory topicDataFactory;
}
