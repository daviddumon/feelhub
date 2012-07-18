package com.steambeat.web;

import com.google.inject.*;
import com.steambeat.web.filter.*;
import com.steambeat.web.guice.SteambeatModule;
import com.steambeat.web.launch.LaunchRouter;
import com.steambeat.web.migration.MigrationRunner;
import com.steambeat.web.migration.web.*;
import com.steambeat.web.status.SteambeatStatusService;
import com.steambeat.web.tools.SteambeatWebProperties;
import freemarker.template.*;
import org.restlet.*;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import org.restlet.service.TaskService;

import javax.servlet.ServletContext;
import java.util.Locale;

public class SteambeatApplication extends Application {

    public SteambeatApplication(final Context context) {
        super(context);
        setStatusService(new SteambeatStatusService());
    }

    @Override
    public synchronized void start() throws Exception {
        steambeatWebProperties = new SteambeatWebProperties();
        setCookieParametersInContext();
        initFreemarkerConfiguration();
        setStatus();
        if (!getContext().getAttributes().get("com.steambeat.status").equals("launch")) {
            final SteambeatBoot steambeatBoot = injector.getInstance(SteambeatBoot.class);
            steambeatBoot.checkForSteam();
            runMigrations();
        }
        super.start();
    }

    private void setCookieParametersInContext() {
        getContext().getAttributes().put("com.steambeat.cookie.domain", steambeatWebProperties.getCookie());
        getContext().getAttributes().put("com.steambeat.cookie.secure", steambeatWebProperties.getSecureMode());
    }

    private void setStatus() {
        getContext().getAttributes().put("com.steambeat.status", steambeatWebProperties.getStatus());
    }

    private void runMigrations() {
        setReadyContext();
        final TaskService taskService = getTaskService();
        final MigrationRunner migrationRunner = injector.getInstance(MigrationRunner.class);
        migrationRunner.setContext(getContext());
        taskService.execute(migrationRunner);
    }

    private void initFreemarkerConfiguration() throws TemplateModelException {
        final Configuration configuration = new Configuration();
        configuration.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
        configuration.setEncoding(Locale.ROOT, "UTF-8");
        configuration.addAutoImport("head", "/base/head.ftl");
        configuration.addAutoImport("normal", "/base/normal.ftl");
        configuration.addAutoImport("info", "/base/info.ftl");
        configuration.setSharedVariable("dev", steambeatWebProperties.isDev());
        configuration.setSharedVariable("root", steambeatWebProperties.getDomain() + servletContext().getContextPath());
        configuration.setSharedVariable("buildtime", steambeatWebProperties.getBuildTime());
        getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private void setReadyContext() {
        getContext().getAttributes().put("com.steambeat.ready", new Boolean(steambeatWebProperties.getReadyState()));
    }

    public void setModule(final Module module) {
        injector = Guice.createInjector(module);
    }

    @Override
    public Restlet createInboundRoot() {
        final Router router = new Router(getContext());
        router.attach("/static", new Directory(getContext(), "war:///static"));
        setCorrectRouter(router);
        return router;
    }

    private void setCorrectRouter(final Router router) {
        if (getContext().getAttributes().get("com.steambeat.status").equals("launch")) {
            router.attach(new LaunchRouter(getContext(), injector));
        } else {
            router.attach(getOpenSessionInViewFilter());
        }
    }

    private OpenSessionInViewFilter getOpenSessionInViewFilter() {
        final OpenSessionInViewFilter filter = injector.getInstance(OpenSessionInViewFilter.class);
        filter.setContext(getContext());
        filter.setNext(getIdentityFilter());
        return filter;
    }

    private IdentityFilter getIdentityFilter() {
        final IdentityFilter filter = injector.getInstance(IdentityFilter.class);
        filter.setContext(getContext());
        filter.setNext(getMigrationFilter());
        return filter;
    }

    private MigrationFilter getMigrationFilter() {
        final MigrationFilter filter = injector.getInstance(MigrationFilter.class);
        filter.setSteambeatRouter(new SteambeatRouter(getContext(), injector));
        filter.setMigrationRouter(new MigrationRouter(getContext(), injector));
        filter.setContext(getContext());
        filter.setNext(new SteambeatRouter(getContext(), injector));
        return filter;
    }

    private SteambeatWebProperties steambeatWebProperties;
    private Injector injector = Guice.createInjector(new SteambeatModule());
}