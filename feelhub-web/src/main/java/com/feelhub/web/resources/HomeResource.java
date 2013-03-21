package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import com.feelhub.web.resources.authentification.SignupResource;
import com.google.inject.Inject;
import org.restlet.data.Form;
import org.restlet.resource.*;

import java.util.List;

public class HomeResource extends ServerResource {

    @Inject
    public HomeResource(final ApiFeelingSearch apiFeelingSearch, final SignupResource signupResource) {
        this.apiFeelingSearch = apiFeelingSearch;
        this.signupResource = signupResource;
    }

    @Get
    public ModelAndView getHome() {
        if (CurrentUser.get().isAnonymous()) {
            signupResource.setRequest(getRequest());
            return signupResource.represent();
        }
        return ModelAndView.createNew("home.ftl").with("locales", FeelhubLanguage.availables()).with("feelingDatas", getInitialFeelingDatas());
    }

    private List<FeelingData> getInitialFeelingDatas() {
        final Form parameters = new Form();
        parameters.add("skip", "0");
        parameters.add("limit", "20");
        return apiFeelingSearch.doSearch(parameters);
    }

    private final ApiFeelingSearch apiFeelingSearch;
    private SignupResource signupResource;
}