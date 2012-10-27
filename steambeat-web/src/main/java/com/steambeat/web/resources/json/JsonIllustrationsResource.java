package com.steambeat.web.resources.json;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.steambeat.domain.illustration.Illustration;
import com.steambeat.web.representation.ModelAndView;
import com.steambeat.web.search.IllustrationSearch;
import org.json.JSONException;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.List;
import java.util.UUID;

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
