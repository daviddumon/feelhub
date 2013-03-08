package com.feelhub.web.resources;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.resources.api.ApiFeelingSearch;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.Form;
import org.restlet.resource.*;

import java.util.List;

public class MyFeelingsResource extends ServerResource {

    @Inject
    public MyFeelingsResource(final ApiFeelingSearch apiFeelingSearch) {
        this.apiFeelingSearch = apiFeelingSearch;
    }

    @Get
    public ModelAndView getMyFeelings() {
        try {
            checkCredentials();
            return ModelAndView.createNew("myfeelings.ftl").with("locales", FeelhubLanguage.availables()).with("feelingDatas", getInitialFeelingDatas());
        } catch (AuthenticationException e) {
            redirectSeeOther(new WebReferenceBuilder(getContext()).buildUri("/login"));
            return ModelAndView.createNew("empty.ftl");
        }
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }

    private List<FeelingData> getInitialFeelingDatas() {
        final Form parameters = new Form();
        parameters.add("skip", "0");
        parameters.add("limit", "20");
        return apiFeelingSearch.doSearch(parameters, CurrentUser.get().getUser());
    }

    private ApiFeelingSearch apiFeelingSearch;
}
