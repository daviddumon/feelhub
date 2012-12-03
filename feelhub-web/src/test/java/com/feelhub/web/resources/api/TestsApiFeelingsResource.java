package com.feelhub.web.resources.api;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.feeling.FeelingRequestEvent;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.guice.GuiceTestModule;
import com.google.inject.*;
import org.json.*;
import org.junit.*;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.restlet.*;
import org.restlet.data.*;
import org.restlet.ext.json.JsonRepresentation;

import java.util.UUID;

import static org.fest.assertions.Assertions.*;

public class TestsApiFeelingsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithDomainEvent events = new WithDomainEvent();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        feelingsResource = injector.getInstance(ApiFeelingsResource.class);
        feelingsResource.setResponse(new Response(new Request()));
    }

    @After
    public void tearDown() throws Exception {
        CurrentUser.set(null);
    }

    @Test
    public void canGetFeelings() throws JSONException {
        final ClientResource resource = restlet.newClientResource("/api/feelings");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canPostFeeling() {
        feelingsResource.add(goodForm());

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.SUCCESS_CREATED);
    }

    @Test
    public void errorIfMissingText() {
        feelingsResource.add(badFormWithoutText());

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void errorIfMissingLanguageCode() {
        feelingsResource.add(badFormWithoutLanguage());

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void errorIfMissingTopic() {
        feelingsResource.add(badFormWithoutTopic());

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_BAD_REQUEST);
    }

    @Test
    public void doNotCreateFeelingOnError() {
        feelingsResource.add(badFormWithoutLanguage());

        assertThat(Repositories.feelings().getAll().size()).isZero();
    }

    @Test
    public void mustBeAuthentificated() {
        CurrentUser.set(WebUser.anonymous());

        feelingsResource.add(goodForm());

        assertThat(feelingsResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_UNAUTHORIZED);
    }

    @Test
    public void returnFeelingId() throws JSONException {
        events.capture(FeelingRequestEvent.class);

        final JsonRepresentation jsonResponse = feelingsResource.add(goodForm());

        final FeelingRequestEvent feelingRequestEvent = events.lastEvent(FeelingRequestEvent.class);
        final JSONObject jsonData = jsonResponse.getJsonObject();
        assertThat(jsonData.get("id").toString()).isEqualTo(feelingRequestEvent.getFeelingId().toString());
    }

    private Form goodForm() {
        final Form parameters = new Form();
        parameters.add("topicId", UUID.randomUUID().toString());
        parameters.add("text", "my feeling +sentiment");
        parameters.add("language", FeelhubLanguage.fromCode("fr").getCode());
        return parameters;
    }

    private Form badFormWithoutText() {
        final Form parameters = new Form();
        parameters.add("topicId", UUID.randomUUID().toString());
        parameters.add("language", FeelhubLanguage.fromCode("fr").getCode());
        return parameters;
    }

    private Form badFormWithoutTopic() {
        final Form parameters = new Form();
        parameters.add("text", "my feeling +sentiment");
        parameters.add("language", FeelhubLanguage.fromCode("fr").getCode());
        return parameters;
    }

    private Form badFormWithoutLanguage() {
        final Form parameters = new Form();
        parameters.add("topicId", UUID.randomUUID().toString());
        parameters.add("text", "my feeling +sentiment");
        return parameters;
    }

    private User user;
    private ApiFeelingsResource feelingsResource;
}