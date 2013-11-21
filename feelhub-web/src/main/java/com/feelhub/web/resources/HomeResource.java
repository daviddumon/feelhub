package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.topics.ApiTopicsLiveResource;
import com.google.inject.Inject;
import org.restlet.data.Language;
import org.restlet.resource.*;

public class HomeResource extends ServerResource {

    @Inject
    public HomeResource(final ApiTopicsLiveResource apiTopicsLiveResource) {
        this.apiTopicsLiveResource = apiTopicsLiveResource;
    }

    @Get
    public ModelAndView represent() {
        if (CurrentUser.get().isAnonymous()) {
            return getWelcome();
        } else {
            return getHome();
        }
    }

    private ModelAndView getWelcome() {
        return ModelAndView.createNew("welcome.ftl")
                .with("locales", FeelhubLanguage.availables())
                .with("preferedLanguage", getPreferedLanguage().getPrimaryTag());
    }

    private ModelAndView getHome() {
        final ModelAndView modelAndView = ModelAndView.createNew("home.ftl")
                .with("topicDatas", apiTopicsLiveResource.getTopicDatas(0, 50))
                .with("locales", FeelhubLanguage.availables())
                .with("preferedLanguage", getPreferedLanguage().getPrimaryTag());

        if (CurrentUser.get().bookmarkletShow()) {
            modelAndView.with("bookmarkletShow", true);
        }

        return modelAndView;
    }

    private Language getPreferedLanguage() {
        if (getRequest().getClientInfo().getAcceptedLanguages().isEmpty()) {
            return Language.ENGLISH;
        }
        return getRequest().getClientInfo().getAcceptedLanguages().get(0).getMetadata();
    }

    private final ApiTopicsLiveResource apiTopicsLiveResource;
}