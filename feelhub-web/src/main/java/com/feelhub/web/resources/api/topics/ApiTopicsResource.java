package com.feelhub.web.resources.api.topics;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.topic.*;
import com.feelhub.domain.topic.TopicIdentifier;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.resources.api.FeelhubApiException;
import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.UUID;

public class ApiTopicsResource extends ServerResource {

    @Inject
    public ApiTopicsResource(final CommandBus bus) {
        this.bus = bus;
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
        final CreateHttpTopicCommand command = new CreateHttpTopicCommand(name, CurrentUser.get().getUser().getId());
        final ListenableFuture<UUID> result = bus.execute(command);
        final UUID topicId = Futures.getUnchecked(result);
        setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + topicId));
        setStatus(Status.SUCCESS_CREATED);
    }

    private void createRealTopic(final Form form, final String name) {
        final RealTopicType type = extractType(form);
        final CreateRealTopicCommand command = new CreateRealTopicCommand(CurrentUser.get().getLanguage(), name, type, CurrentUser.get().getUser().getId());
        final UUID topicId = Futures.getUnchecked(bus.execute(command));
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

    private final CommandBus bus;
}
