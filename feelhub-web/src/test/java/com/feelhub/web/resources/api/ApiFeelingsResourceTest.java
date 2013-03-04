package com.feelhub.web.resources.api;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.google.inject.*;
import org.apache.http.auth.AuthenticationException;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.data.*;
import org.restlet.ext.json.JsonRepresentation;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class ApiFeelingsResourceTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        apiFeelingSearchMock = mock(ApiFeelingSearch.class);
        apiCreateFeelingMock = mock(ApiCreateFeeling.class);
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ApiFeelingSearch.class).toInstance(apiFeelingSearchMock);
                bind(ApiCreateFeeling.class).toInstance(apiCreateFeelingMock);
            }
        });
        feelingsResource = injector.getInstance(ApiFeelingsResource.class);
        CurrentUser.set(new WebUser(new User(), true));
        ContextTestFactory.initResource(feelingsResource);
    }

    @Test
    public void isMappedGetFeelings() throws JSONException {
        final ClientResource resource = restlet.newClientResource("/api/feelings");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void doSearchFeelings() throws JSONException {
        feelingsResource.getFeelings();

        verify(apiFeelingSearchMock).doSearch(any(Form.class));
    }

    @Test
    public void canPostFeeling() throws JSONException, AuthenticationException {
        feelingsResource.add(new JsonRepresentation("{}"));

        verify(apiCreateFeelingMock).add(any(JSONObject.class));
    }

    @Test
    public void statusErrorOnError() throws JSONException, AuthenticationException {
        when(apiCreateFeelingMock.add(any(JSONObject.class))).thenThrow(new JSONException(""));

        feelingsResource.add(new JsonRepresentation("{}"));

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void doNotCreateFeelingOnError() throws JSONException, AuthenticationException {
        when(apiCreateFeelingMock.add(any(JSONObject.class))).thenThrow(new JSONException(""));

        feelingsResource.add(new JsonRepresentation("{}"));

        assertThat(Repositories.feelings().getAll().size()).isZero();
    }

    private ApiFeelingsResource feelingsResource;
    private ApiFeelingSearch apiFeelingSearchMock;
    private ApiCreateFeeling apiCreateFeelingMock;

}