package com.steambeat.web.resources;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.application.JudgmentDTO;
import com.steambeat.application.OpinionService;
import com.steambeat.application.WebPageNotYetCreatedException;
import com.steambeat.domain.opinion.Feeling;
import com.steambeat.domain.subject.webpage.Uri;
import com.steambeat.web.ReferenceBuilder;
import com.steambeat.web.search.OpinionSearch;
import org.restlet.data.Form;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class WebPageOpinionsResource extends ServerResource {

    @Inject
    public WebPageOpinionsResource(final OpinionService opinionService, final OpinionSearch opinionSearch) {
        this.opinionService = opinionService;
        this.opinionSearch = opinionSearch;
    }

    @Override
    protected void doInit() throws ResourceException {
        uri = new Uri(Reference.decode(getRequestAttributes().get("uri").toString()));
    }

    @Post
    public void post(final Form form) {
        checkHasFeeling(form);
        final String feeling1 = form.getFirstValue("feeling");
        final Feeling feeling = Feeling.valueOf(feeling1);
        final String text = form.getFirstValue("text");
        try {
            final JudgmentDTO judgmentDTO = new JudgmentDTO(uri.toString(), feeling1);
            opinionService.addOpinion(text, Lists.newArrayList(judgmentDTO));
            setStatus(Status.SUCCESS_CREATED);
            setLocationRef(new ReferenceBuilder(getContext()).buildUri("/webpages/" + uri));
        } catch (WebPageNotYetCreatedException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private void checkHasFeeling(final Form form) {
        if (form.getFirstValue("feeling") == null) {
            throw new ResourceException(Status.CLIENT_ERROR_BAD_REQUEST);
        }
    }

    private final OpinionService opinionService;
    private final OpinionSearch opinionSearch;
    private Uri uri;
}
