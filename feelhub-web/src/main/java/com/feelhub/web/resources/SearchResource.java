package com.feelhub.web.resources;

import com.feelhub.application.TagService;
import com.feelhub.domain.topic.Topic;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.util.List;

public class SearchResource extends ServerResource {

    @Inject
    public SearchResource(final TagService tagService) {
        this.tagService = tagService;
    }

    @Get
    public ModelAndView search() {
        final String query = getRequestAttributes().get("q").toString().trim();
        setStatus(Status.SUCCESS_OK);
        final List<Topic> topics = tagService.search(query);
        if (topics.isEmpty()) {
            return redirectToNewResource(query);
        } else if (topics.size() == 1) {
            return redirectToTopic(topics.get(0));
        } else {
            return ModelAndView.createNew("search.ftl").with("topics", topics);
        }
    }

    private ModelAndView redirectToTopic(final Topic topic) {
        final WebReferenceBuilder webReferenceBuilder = new WebReferenceBuilder(getContext());
        setStatus(Status.REDIRECTION_SEE_OTHER);
        setLocationRef(webReferenceBuilder.buildUri("/topic/" + topic.getId()));
        return ModelAndView.empty();
    }

    private ModelAndView redirectToNewResource(final String query) {
        final WebReferenceBuilder webReferenceBuilder = new WebReferenceBuilder(getContext());
        setStatus(Status.REDIRECTION_SEE_OTHER);
        setLocationRef(webReferenceBuilder.buildUri("/newtopic/" + query));
        return ModelAndView.empty();
    }

    private TagService tagService;
}
