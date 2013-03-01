package com.feelhub.web.resources.api;

import com.feelhub.domain.user.User;
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
import org.restlet.data.Status;
import org.restlet.ext.freemarker.TemplateRepresentation;

import java.io.IOException;

import static org.fest.assertions.Assertions.*;

public class TestsApiMyFeelingsResource {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        final ApiMyFeelingsResource apiMyFeelingsResource = injector.getInstance(ApiMyFeelingsResource.class);
        ContextTestFactory.initResource(apiMyFeelingsResource);
    }

    @Test
    public void errorIfBadUser() {
        final ClientResource clientResource = restlet.newClientResource("/api/myfeelings");

        clientResource.get();

        assertThat(clientResource.getStatus()).isEqualTo(Status.CLIENT_ERROR_UNAUTHORIZED);
    }

    @Test
    @Ignore("merci le bordel pour les tests maintenant")
    public void hasFeelings() throws IOException, JSONException {
        TestFactories.feelings().newFeeling(user.getId());
        TestFactories.feelings().newFeeling(user.getId());
        TestFactories.feelings().newFeeling();
        final ClientResource resource = restlet.newClientResource("/api/myfeelings");

        final TemplateRepresentation representation = (TemplateRepresentation) resource.get();

        assertThat(representation).isNotNull();
        final JSONArray jsonArray = new JSONArray(representation.getText());
        assertThat(jsonArray).isNotNull();
        assertThat(jsonArray.length()).isEqualTo(2);
    }

    private User user;
}
