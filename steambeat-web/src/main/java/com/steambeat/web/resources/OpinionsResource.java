package com.steambeat.web.resources;

import com.steambeat.domain.opinion.Opinion;
import com.steambeat.web.search.OpinionSearch;
import org.json.*;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.inject.Inject;
import java.util.List;

public class OpinionsResource extends ServerResource {

    @Inject
    public OpinionsResource(final OpinionSearch opinionSearch) {
        this.opinionSearch = opinionSearch;
    }

    @Override
    protected void doInit() {
        System.out.println(getRequestAttributes().containsKey("subject"));
        System.out.println(getQuery());
        lastOpinions = opinionSearch.last();
    }

    @Get
    public Representation represent() throws JSONException {
        final JSONArray result = new JSONArray();
        for (final Opinion opinion : lastOpinions) {
            result.put(toJson(opinion));
        }
        return new JsonRepresentation(result);
    }

    private JSONObject toJson(final Opinion opinion) throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("subjectId", opinion.getSubjectId());
        json.put("text", opinion.getText());
        json.put("feeling", opinion.getFeeling());
        return json;
    }

    private List<Opinion> lastOpinions;
    private final OpinionSearch opinionSearch;
}
