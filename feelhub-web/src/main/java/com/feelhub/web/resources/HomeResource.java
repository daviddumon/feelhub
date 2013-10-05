package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.dto.TopicData;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.data.Language;
import org.restlet.resource.*;

public class HomeResource extends ServerResource {

    @Inject
    public HomeResource() {

    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("home.ftl")
                .with("topicDatas", new TopicData.Builder().build())
                .with("locales", FeelhubLanguage.availables())
                .with("preferedLanguage", getPreferedLanguage().getPrimaryTag());
    }

    private Language getPreferedLanguage() {
        if (getRequest().getClientInfo().getAcceptedLanguages().isEmpty()) {
            return Language.ENGLISH;
        }
        return getRequest().getClientInfo().getAcceptedLanguages().get(0).getMetadata();
    }
}