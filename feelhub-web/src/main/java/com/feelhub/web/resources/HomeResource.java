package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.representation.ModelAndView;
import org.restlet.resource.*;

public class HomeResource extends ServerResource {

    @Get
    public ModelAndView getHome() {
        return ModelAndView.createNew("home.ftl").with("locales", FeelhubLanguage.availables());
    }
}