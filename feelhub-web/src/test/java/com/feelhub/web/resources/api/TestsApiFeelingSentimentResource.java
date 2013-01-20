package com.feelhub.web.resources.api;

import com.google.inject.*;
import org.junit.*;
import org.restlet.data.Form;

public class TestsApiFeelingSentimentResource {

    @Before
    public void before() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });
        apiFeelingSentimentResource = injector.getInstance(ApiFeelingSentimentResource.class);
    }

    @Test
    public void canPutSentiment() {
        final Form form = new Form();

        apiFeelingSentimentResource.modifySentiment(form);


    }

    private Injector injector;
    private ApiFeelingSentimentResource apiFeelingSentimentResource;
}
