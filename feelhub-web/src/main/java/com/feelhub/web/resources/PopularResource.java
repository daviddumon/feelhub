package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.topics.ApiTopicsPopularResource;
import com.google.inject.Inject;
import org.restlet.data.Language;
import org.restlet.resource.*;

public class PopularResource extends ServerResource {

    @Inject
    public PopularResource(final ApiTopicsPopularResource apiTopicsPopularResource) {
        this.apiTopicsPopularResource = apiTopicsPopularResource;
    }

    @Get
    public ModelAndView represent() {
        final ModelAndView modelAndView = ModelAndView.createNew("popular.ftl")
                .with("topicDatas", apiTopicsPopularResource.getTopicDatas(0, 50))
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


    private final ApiTopicsPopularResource apiTopicsPopularResource;
}