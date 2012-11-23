package com.feelhub.web.resources.api;

import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.dto.FeelingData;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.restlet.data.*;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.*;

import java.util.List;

public class ApiFeelingsResource extends ServerResource {

    @Inject
    public ApiFeelingsResource(final ApiCreateFeeling apiCreateFeeling, final ApiFeelingSearch apiFeelingSearch) {
        this.apiCreateFeeling = apiCreateFeeling;
        this.apiFeelingSearch = apiFeelingSearch;
    }

    @Post
    public JsonRepresentation add(final JsonRepresentation jsonRepresentation) {
        try {
            checkCredentials();
            setStatus(Status.SUCCESS_CREATED);
            return apiCreateFeeling.add(jsonRepresentation);
        } catch (JSONException e) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        } catch (AuthenticationException e) {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
        }
        return new JsonRepresentation(new JSONObject());
    }

    @Get
    public ModelAndView getFeelings() throws JSONException {
        final List<FeelingData> feelingDatas = apiFeelingSearch.doSearch(getQuery());
        return ModelAndView.createNew("api/feelings.json.ftl", MediaType.APPLICATION_JSON).with("feelingDatas", feelingDatas);
    }

    private void checkCredentials() throws AuthenticationException {
        if (!CurrentUser.get().isAuthenticated()) {
            throw new AuthenticationException();
        }
    }

    private ApiCreateFeeling apiCreateFeeling;
    private ApiFeelingSearch apiFeelingSearch;
}
