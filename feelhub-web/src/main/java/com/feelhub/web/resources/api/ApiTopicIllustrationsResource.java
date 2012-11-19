package com.feelhub.web.resources.api;

import com.feelhub.application.TopicService;
import com.feelhub.domain.meta.Illustration;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.IllustrationSearch;
import com.google.inject.Inject;
import org.json.JSONException;
import org.restlet.data.MediaType;
import org.restlet.resource.*;

import java.util.*;

public class ApiTopicIllustrationsResource extends ServerResource {

    @Inject
    public ApiTopicIllustrationsResource(final IllustrationSearch illustrationSearch, final TopicService topicService) {
        this.illustrationSearch = illustrationSearch;
        this.topicService = topicService;
    }

    @Get
    public ModelAndView represent() throws JSONException {
        final String topicId = getRequestAttributes().get("topicId").toString().trim();
        final Topic topic = topicService.lookUp(UUID.fromString(topicId));
        final List<Illustration> illustrations = illustrationSearch.withTopicId(topic.getId()).execute();
        return ModelAndView.createNew("api/illustrations.json.ftl", MediaType.APPLICATION_JSON).with("illustrations", illustrations);
    }

    private final IllustrationSearch illustrationSearch;
    private TopicService topicService;
}
