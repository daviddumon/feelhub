package com.steambeat.web;

import com.google.inject.AbstractModule;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.guice.SteambeatModuleForTest;
import com.steambeat.web.resources.WebPageResource;
import freemarker.template.Configuration;
import org.junit.*;
import org.restlet.Context;

import javax.servlet.ServletContext;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("deprecation")
public class TestsSteambeatApplication {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canInitFeemarker() throws Exception {
        final Context context = createContext();
        final SteambeatApplication application = new SteambeatApplication(context);
        application.setModule(new SteambeatModuleForTest());

        application.start();

        assertThat(context.getAttributes(), hasKey("org.freemarker.Configuration"));
        final Configuration configuration = (Configuration) context.getAttributes().get("org.freemarker.Configuration");
        assertThat(configuration, notNullValue());
    }

    private Context createContext() {
        final Context context = new Context();
        final ServletContext servletContext = mock(ServletContext.class);
        context.getAttributes().put("org.restlet.ext.servlet.ServletContext", servletContext);
        return context;
    }

    private class TestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(WebPageResource.class).to(StubWebPageResource.class);
        }
    }

    public static class StubWebPageResource extends WebPageResource {

        public StubWebPageResource() {
            super(null);
        }
    }
}
