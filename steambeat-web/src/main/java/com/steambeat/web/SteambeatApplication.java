package com.steambeat.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.steambeat.tools.SteambeatProperties;
import com.steambeat.web.guice.SteambeatModule;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Filter;
import org.restlet.routing.Router;

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
        configuration.addAutoImport("head", "/head.ftl");
        configuration.addAutoImport("body", "/body.ftl");
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