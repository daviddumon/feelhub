package com.feelhub.web.resources;

import com.feelhub.web.*;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.*;

public class TestsHomeResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        homeResource = injector.getInstance(HomeResource.class);
    }

    @Test
    public void homeResourceIsMapped() {
        final ClientResource resource = restlet.newClientResource("/");

        resource.get();

        assertThat(resource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

    @Test
    public void hasLocalesInData() {
        final ModelAndView modelAndView = homeResource.getHome();

        assertThat(modelAndView.getData("locales")).isNotNull();
    }

    private HomeResource homeResource;
}
