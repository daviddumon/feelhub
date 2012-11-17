package com.feelhub.web.resources.social;

import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import org.junit.*;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.*;

public class TestsSocialWelcomeResource {

    @Rule
    public WebApplicationTester web = new WebApplicationTester();

    @Test
    public void isMapped() {
        final ClientResource resource = web.newClientResource("/social/welcome");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void canSayWelcome() {
        final SocialWelcomeResource resource = new SocialWelcomeResource();

        final ModelAndView modelAndView = resource.represent();

        assertThat(modelAndView.getTemplate()).isEqualTo("social/welcome.ftl");
    }
}
