package com.feelhub.web.resources;

import com.feelhub.application.TopicService;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.topic.Topic;
import com.feelhub.domain.topic.real.RealTopicType;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.dto.TopicDataFactory;
import com.feelhub.web.representation.ModelAndView;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.util.*;

public class TopicResource extends ServerResource {

    @Inject
    public TopicResource(final TopicService topicService, final TopicDataFactory topicDataFactory) {
        this.topicService = topicService;
        this.topicDataFactory = topicDataFactory;
    }

    @Get
    public ModelAndView getTopic() {
        final Topic topic = topicService.lookUp(extractUriValueFromUri());
        TopicData topicData = topicDataFactory.topicData(topic, CurrentUser.get().getLanguage());
        ModelAndView topicData1 = ModelAndView.createNew("topic.ftl").with("topicData", topicData);
        if (checkCurrent(topic)) {
            final ArrayList<RealTopicType> realTopicTypes = Lists.newArrayList(RealTopicType.values());
            return topicData1.with("locales", FeelhubLanguage.availables())
                    .with("realtypes", realTopicTypes);
        } else {
            setStatus(Status.REDIRECTION_PERMANENT);
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/topic/" + topic.getCurrentId()));
            return topicData1;
        }
    }

    private boolean checkCurrent(final Topic realTopic) {
        return realTopic.getId().equals(realTopic.getCurrentId());
    }

    private UUID extractUriValueFromUri() {
        return UUID.fromString(getRequestAttributes().get("topicId").toString().trim());
    }

    private final TopicService topicService;
    private final TopicDataFactory topicDataFactory;
    private UUID id;
}
