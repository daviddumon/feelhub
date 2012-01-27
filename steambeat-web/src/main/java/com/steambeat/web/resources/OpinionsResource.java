package com.steambeat.web.resources;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.opinion.Opinion;
import com.steambeat.web.SteambeatTemplateRepresentation;
import com.steambeat.web.search.OpinionSearch;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.List;

public class OpinionsResource extends ServerResource {

    @Inject
    public OpinionsResource(final OpinionSearch opinionSearch) {
        this.opinionSearch = opinionSearch;
    }

    @Override
    protected void doInit() throws ResourceException {
        final Form form = getQuery();
        if (!form.isEmpty()) {
            final int skip = Integer.parseInt(form.getFirstValue("skip").trim());
            opinions = opinionSearch.withSkip(skip).withLimit(100).withSort("creationDate", OpinionSearch.REVERSE_ORDER).execute();
        }
        else {
            opinions = opinionSearch.withSort("creationDate", OpinionSearch.REVERSE_ORDER).execute();
        }
    }

    @Get
    public SteambeatTemplateRepresentation represent() throws JSONException {
        return SteambeatTemplateRepresentation.createNew("json/opinions.json.ftl", getContext(), MediaType.APPLICATION_JSON).with("opinions", opinions);
    }

    @Post
    public void post(final Form form) {
        final String text = form.getFirstValue("text");
        //List<Judgment> judgments = Lists.newArrayList();
        //final ListIterator<Parameter> parameterListIterator = form.listIterator();
        //while (parameterListIterator.hasNext()) {
        //    final Parameter next = parameterListIterator.next();
        //    judgments.add(new Judgment(next.getFirst(), Feeling.valueOf(next.getSecond())));
        //}
        setStatus(Status.SUCCESS_CREATED);
    }

    List<Opinion> opinions = Lists.newArrayList();
    private OpinionSearch opinionSearch;
}
