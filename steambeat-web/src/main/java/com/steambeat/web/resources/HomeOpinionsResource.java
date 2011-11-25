package com.steambeat.web.resources;

import com.steambeat.domain.opinion.Opinion;
import com.steambeat.web.search.OpinionSearch;
import com.steambeat.web.tools.JsonExtractor;
import org.json.*;
import org.restlet.ext.json.JsonRepresentation;
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
        opinions = opinionSearch.withSkip(skip).withLimit(limit).execute();
    }

    @Get
    public Representation represent() throws JSONException {
        final JSONArray result = new JSONArray();
        for (final Opinion opinion : opinions) {
            result.put(JsonExtractor.extract(opinion));
        }
        return new JsonRepresentation(result);
    }

    protected List<Opinion> opinions;
    protected final OpinionSearch opinionSearch;
    private int skip;
    private int limit;
}
