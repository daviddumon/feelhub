package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.representation.ModelAndView;
import org.restlet.data.Language;
import org.restlet.resource.*;

public class BookmarkletResource extends ServerResource {

    @Get
    public ModelAndView redirectToTopic() throws Exception {
        return ModelAndView.createNew("bookmarklet.ftl")
                .with("locales", FeelhubLanguage.availables())
                .with("preferedLanguage", getPreferedLanguage().getPrimaryTag())
                .with("uri", getUri());
    }

    private Language getPreferedLanguage() {
        if (getRequest().getClientInfo().getAcceptedLanguages().isEmpty()) {
            return Language.ENGLISH;
        }
        return getRequest().getClientInfo().getAcceptedLanguages().get(0).getMetadata();
    }

    private String getUri() throws Exception {
        if (getQuery().getQueryString().contains("q")) {
            return getQuery().getFirstValue("q");
        } else {
            throw new Exception();
        }
    }
}
