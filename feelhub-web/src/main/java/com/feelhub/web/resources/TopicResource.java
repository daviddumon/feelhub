package com.feelhub.web.resources;

import com.feelhub.application.TopicService;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.util.UUID;

public class TopicResource extends ServerResource {

    @Inject
    public TopicResource(final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView getTopic() {
        extractUriValueFromUri();
        final Topic topic = topicService.lookUp(id);
        if (checkCurrent(topic)) {
            return ModelAndView.createNew("topic.ftl").with("topicData", topicDataFactory.getTopicData(topic, CurrentUser.get().getLanguage())).with("locales", FeelhubLanguage.availables());
        } else {
            setStatus(Status.REDIRECTION_PERMANENT);
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + topic.getCurrentId()));
            return ModelAndView.createNew("topic.ftl").with("topicData", topicDataFactory.getTopicData(topic, CurrentUser.get().getLanguage()));
        }
    }

    private boolean checkCurrent(final Topic realTopic) {
        return realTopic.getId().equals(realTopic.getCurrentId());
    }

    private void extractUriValueFromUri() {
        id = UUID.fromString(getRequestAttributes().get("id").toString());
    }

    private final TopicService topicService;
    private final TopicDataFactory topicDataFactory;
    private UUID id;
}
