package com.feelhub.web.resources;

import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.*;
import com.feelhub.web.authentification.*;
import com.feelhub.web.guice.GuiceTestModule;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.*;

public class MyFeelingsResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final User user = TestFactories.users().createFakeUser("mail@mail.com", "full name");
        CurrentUser.set(new WebUser(user, true));
        final Injector injector = Guice.createInjector(new GuiceTestModule());
        myFeelingsResource = injector.getInstance(MyFeelingsResource.class);
        ContextTestFactory.initResource(myFeelingsResource);
    }

    @Test
    public void redirectIfNotIdentified() {
        final ClientResource resource = restlet.newClientResource("/myfeelings");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.REDIRECTION_SEE_OTHER);
    }

    @Test
    public void hasLocalesInModelData() {
        final ModelAndView modelAndView = myFeelingsResource.getMyFeelings();

        assertThat(modelAndView.getData("locales")).isNotNull();
    }

    private MyFeelingsResource myFeelingsResource;
}
