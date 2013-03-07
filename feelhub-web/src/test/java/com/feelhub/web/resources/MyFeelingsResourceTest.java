package com.feelhub.web.resources;

import com.feelhub.domain.user.User;
import com.feelhub.test.TestFactories;
import com.feelhub.web.ClientResource;
import com.feelhub.web.ContextTestFactory;
import com.feelhub.web.WebApplicationTester;
import com.feelhub.web.authentification.CurrentUser;
import com.feelhub.web.authentification.WebUser;
import com.feelhub.web.guice.GuiceTestModule;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.*;

public class MyFeelingsResourceTest {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

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
