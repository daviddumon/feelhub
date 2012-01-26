package com.steambeat.web.resources;

import com.steambeat.domain.opinion.Opinion;
import com.steambeat.web.SteambeatTemplateRepresentation;
import com.steambeat.web.search.OpinionSearch;
import org.json.JSONException;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.inject.Inject;
import java.util.List;

public class HomeOpinionsResource extends ServerResource {

    @Inject
    public HomeOpinionsResource(final OpinionSearch opinionSearch) {
        this.opinionSearch = opinionSearch;
    }

    @Override
    protected void doInit() {
        getParameters();
        getOpinions();
    }

    private void getParameters() {
        skip = Integer.valueOf(getRequestAttributes().get("skip").toString());
        limit = Integer.valueOf(getRequestAttributes().get("limit").toString());
        if (limit > 100) {
            throw new SteambeatJsonException();
        }
    }

    private void getOpinions() {
        opinions = opinionSearch.withSkip(skip).withLimit(limit).withSort("creationDate", OpinionSearch.REVERSE_ORDER).execute();
    }

    @Get
    public Representation represent() throws JSONException {
        return SteambeatTemplateRepresentation.createNew("json/opinions.json.ftl", getContext(), MediaType.APPLICATION_JSON).with("opinions", opinions);
    }

    protected List<Opinion> opinions;
    protected final OpinionSearch opinionSearch;
    private int skip;
    private int limit;
}
