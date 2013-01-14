package com.feelhub.web.resources.api;

import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.List;

public class ApiMyFeelingsResource extends ServerResource {

    @Inject
    public ApiMyFeelingsResource(final ApiFeelingSearch apiFeelingSearch) {
        this.apiFeelingSearch = apiFeelingSearch;
    }

    @Get
    public ModelAndView getMyFeelings() {
        try {
            checkCredentials();
            setStatus(Status.SUCCESS_OK);
            final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(getQuery(), CurrentUser.get().getUser());
            return ModelAndView.createNew("api/feelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
        } catch (AuthenticationException e) {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
            return ModelAndView.createNew("empty.ftl");
        }
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }

    private ApiFeelingSearch apiFeelingSearch;
}
