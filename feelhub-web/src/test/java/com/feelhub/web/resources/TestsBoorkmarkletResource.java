package com.feelhub.web.resources;

import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.web.*;
import com.google.inject.*;
import org.junit.*;
import org.restlet.data.Status;

import static org.fest.assertions.Assertions.*;

public class TestsBoorkmarkletResource {

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Before
    public void before() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        final BookmarkletResource bookmarkletResource = injector.getInstance(BookmarkletResource.class);
        ContextTestFactory.initResource(bookmarkletResource);
    }

    @Test
    public void isMapped() {
        final ClientResource bookmarkletResource = restlet.newClientResource("/bookmarklet?q=www.google.fr");

        bookmarkletResource.get();

        assertThat(bookmarkletResource.getStatus()).isEqualTo(Status.SUCCESS_OK);
    }

}
