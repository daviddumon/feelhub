package com.steambeat.web;

import com.google.inject.*;
import com.steambeat.tools.SteambeatWebProperties;
import com.steambeat.web.guice.SteambeatModule;
import com.steambeat.web.status.SteambeatStatusService;
import freemarker.template.*;
import org.restlet.*;
import org.restlet.resource.Directory;
import org.restlet.routing.*;

import javax.servlet.ServletContext;
import java.util.Locale;

public class SteambeatApplication extends Application {

    public SteambeatApplication(final Context context) {
        super(context);
        setStatusService(new SteambeatStatusService());
    }

    @Override
    public synchronized void start() throws Exception {
        initFreemarkerConfiguration();
        final SteambeatBoot steambeatBoot = injector.getInstance(SteambeatBoot.class);
        steambeatBoot.checkForSteam();
        super.start();
    }

    private void initFreemarkerConfiguration() throws TemplateModelException {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();
        final Configuration configuration = new Configuration();
        configuration.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
        configuration.setEncoding(Locale.ROOT, "UTF-8");
        configuration.addAutoImport("head", "/head.ftl");
        configuration.addAutoImport("body", "/body.ftl");
        configuration.setSharedVariable("root", servletContext().getContextPath());
        configuration.setSharedVariable("dev", steambeatWebProperties.isDev());
        configuration.setSharedVariable("domain", steambeatWebProperties.getDomain());
        configuration.setSharedVariable("buildtime", steambeatWebProperties.getBuildTime());
        getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    @Override
    public Restlet createInboundRoot() {
        final Router router = new Router(getContext());
        router.attach("/static", new Directory(getContext(), "war:///static"));
        final Filter openSession = injector.getInstance(OpenSessionInViewFilter.class);
        openSession.setContext(getContext());
        final SteambeatRouter steambeatRouter = new SteambeatRouter(getContext(), injector);
        openSession.setNext(steambeatRouter);
        router.attach(openSession);
        return router;
    }

    public void setModule(final Module module) {
        injector = Guice.createInjector(module);
    }

    private Injector injector = Guice.createInjector(new SteambeatModule());
}