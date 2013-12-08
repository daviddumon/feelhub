package com.feelhub.web.resources;

import com.feelhub.domain.feeling.FeelingValue;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.topics.ApiTopicsSearchResource;
import com.google.inject.Inject;
import org.restlet.data.Language;
import org.restlet.resource.*;

public class InstallBookmarkletResource extends ServerResource {

    @Inject
    public InstallBookmarkletResource(final ApiTopicsSearchResource apiTopicsLiveResource) {
        this.apiTopicsLiveResource = apiTopicsLiveResource;
    }

    @Get
    public ModelAndView represent() {
        final ModelAndView modelAndView = ModelAndView.createNew("home.ftl")
                .with("feelingValues", FeelingValue.values())
                .with("locales", FeelhubLanguage.availables())
                .with("preferedLanguage", getPreferedLanguage().getPrimaryTag());

        modelAndView.with("bookmarkletShow", true);
        return modelAndView;
    }

    private Language getPreferedLanguage() {
        if (getRequest().getClientInfo().getAcceptedLanguages().isEmpty()) {
            return Language.ENGLISH;
        }
        return getRequest().getClientInfo().getAcceptedLanguages().get(0).getMetadata();
    }

    private final ApiTopicsSearchResource apiTopicsLiveResource;
}