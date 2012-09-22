package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.illustration.Illustration;
import com.steambeat.web.representation.SteambeatTemplateRepresentation;
import com.steambeat.web.search.IllustrationSearch;
import org.json.JSONException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.*;

public class JsonIllustrationsResource extends ServerResource {

    @Inject
    public JsonIllustrationsResource(final IllustrationSearch illustrationSearch) {
        this.illustrationSearch = illustrationSearch;
    }

    @Get
    public SteambeatTemplateRepresentation represent() throws JSONException {
        final Form form = getQuery();
        final String[] referenceIdsAsString = form.getFirstValue("referenceId").trim().split(",");
        final List<UUID> referenceIds = Lists.newArrayList();
        for (int i = 0; i < referenceIdsAsString.length; i++) {
            referenceIds.add(UUID.fromString(referenceIdsAsString[i]));
        }
        final List<Illustration> illustrations = illustrationSearch.withReferences(referenceIds).execute();
        return SteambeatTemplateRepresentation.createNew("json/illustrations.json.ftl", getContext(), MediaType.APPLICATION_JSON, getRequest()).with("illustrations", illustrations);
    }

    private IllustrationSearch illustrationSearch;
}
