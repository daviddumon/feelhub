package com.steambeat.web;

import com.google.inject.*;
import com.steambeat.tools.SteambeatProperties;
import com.steambeat.web.guice.SteambeatModule;
import freemarker.template.*;
import org.restlet.*;
import org.restlet.resource.Directory;
import org.restlet.routing.*;

import javax.servlet.ServletContext;
import java.util.Locale;

public class SteambeatApplication extends Application {

    public SteambeatApplication(final Context context) {
        super(context);
        setStatusService(new StatusServiceSteambeat());
    }

    @Override
    public synchronized void start() throws Exception {
        initFreemarkerConfiguration();
        super.start();
    }

    private void initFreemarkerConfiguration() throws TemplateModelException {
        final SteambeatProperties steambeatProperties = new SteambeatProperties();
        final Configuration configuration = new Configuration();
        configuration.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
        configuration.setEncoding(Locale.ROOT, "UTF-8");
        configuration.addAutoImport("header", "/header.ftl");
        configuration.addAutoImport("layout", "/layout.ftl");
        configuration.setSharedVariable("root", servletContext().getContextPath());
        configuration.setSharedVariable("dev", steambeatProperties.isDev());
        configuration.setSharedVariable("domain", steambeatProperties.getDomain());
        getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    @Override
    public Restlet createInboundRoot() {
        final Router router = new Router(getContext());
        router.attach("/static", new Directory(getContext(), "war:///static"));
        final SteambeatRouter steambeatRouter = new SteambeatRouter(getContext(), injector);
        final Filter openSession = injector.getInstance(OpenSessionInViewFilter.class);
        openSession.setContext(getContext());
        openSession.setNext(steambeatRouter);
        router.attach(openSession);
        return router;
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    public void setModule(final Module module) {
        injector = Guice.createInjector(module);
    }

    private Injector injector = Guice.createInjector(new SteambeatModule());
}