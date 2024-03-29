package com.feelhub.web;

import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.web.guice.GuiceTestModule;
import freemarker.template.Configuration;
import org.junit.*;
import org.restlet.Context;

import javax.servlet.ServletContext;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class FeelhubApplicationTest {

    @Rule
    public WithDomainEvent event = new WithDomainEvent();

    @Test
    public void canInitFeemarker() throws Exception {

        final Context context = createContext();
        final FeelhubApplication application = new FeelhubApplication(context);
        application.initializeGuice(new GuiceTestModule());

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
}
