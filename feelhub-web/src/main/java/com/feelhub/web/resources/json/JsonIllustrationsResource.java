package com.feelhub.web.resources.json;

import com.feelhub.domain.illustration.Illustration;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.search.IllustrationSearch;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
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
    public ModelAndView represent() throws JSONException {
        final Form form = getQuery();
        final String[] referenceIdsAsString = form.getFirstValue("referenceId").trim().split(",");
        final List<UUID> referenceIds = Lists.newArrayList();
        for (int i = 0; i < referenceIdsAsString.length; i++) {
            referenceIds.add(UUID.fromString(referenceIdsAsString[i]));
        }
        final List<Illustration> illustrations = illustrationSearch.withReferences(referenceIds).execute();
        return ModelAndView.createNew("json/illustrations.json.ftl", MediaType.APPLICATION_JSON).with("illustrations", illustrations);
    }

    private final IllustrationSearch illustrationSearch;
}
