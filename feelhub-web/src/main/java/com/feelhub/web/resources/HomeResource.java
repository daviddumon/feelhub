package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.topics.ApiTopicsLastFeelingsResource;
import com.google.inject.Inject;
import org.restlet.data.Language;
import org.restlet.resource.*;

public class HomeResource extends ServerResource {

    @Inject
    public HomeResource(final ApiTopicsLastFeelingsResource apiTopicsLastFeelingsResource) {
        this.apiTopicsLastFeelingsResource = apiTopicsLastFeelingsResource;
    }

    @Get
    public ModelAndView represent() {
        final ModelAndView modelAndView = ModelAndView.createNew("home.ftl")
                .with("topicDatas", apiTopicsLastFeelingsResource.getTopicDatas(0, 50))
                .with("locales", FeelhubLanguage.availables())
                .with("preferedLanguage", getPreferedLanguage().getPrimaryTag());
        if (CurrentUser.get().welcomePanelShow()) {
            modelAndView.with("welcomePanelShow", true);
            CurrentUser.get().getUser().setWelcomePanelShow(false);
        }
        return modelAndView;
    }

    private Language getPreferedLanguage() {
        if (getRequest().getClientInfo().getAcceptedLanguages().isEmpty()) {
            return Language.ENGLISH;
        }
        return getRequest().getClientInfo().getAcceptedLanguages().get(0).getMetadata();
    }

    private final ApiTopicsLastFeelingsResource apiTopicsLastFeelingsResource;
}