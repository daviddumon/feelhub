package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.List;

public class HomeResource extends ServerResource {

    @Inject
    public HomeResource(final ApiFeelingSearch apiFeelingSearch) {
        this.apiFeelingSearch = apiFeelingSearch;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("home.ftl")
                .with("locales", FeelhubLanguage.availables())
                .with("feelingDatas", getInitialFeelingDatas())
                .with("preferedLanguage", getPreferedLanguage().getPrimaryTag());
    }

    private Language getPreferedLanguage() {
        if (getRequest().getClientInfo().getAcceptedLanguages().isEmpty()) {
            return Language.ENGLISH;
        }
        return getRequest().getClientInfo().getAcceptedLanguages().get(0).getMetadata();
    }

    private List<FeelingData> getInitialFeelingDatas() {
        final Form parameters = new Form();
        parameters.add("skip", "0");
        parameters.add("limit", "20");
        return apiFeelingSearch.doSearch(parameters);
    }

    private final ApiFeelingSearch apiFeelingSearch;
}