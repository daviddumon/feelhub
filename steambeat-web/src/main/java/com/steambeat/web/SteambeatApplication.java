package com.steambeat.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.steambeat.web.filter.IdentityFilter;
import com.steambeat.web.filter.OpenSessionInViewFilter;
import com.steambeat.web.launch.LaunchRouter;
import com.steambeat.web.mail.MailBuilder;
import com.steambeat.web.migration.MigrationRunner;
import com.steambeat.web.migration.web.MigrationFilter;
import com.steambeat.web.migration.web.MigrationRouter;
import com.steambeat.web.status.SteambeatStatusService;
import com.steambeat.web.tools.SteambeatWebProperties;
import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
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

    public void initializeGuice(final Module module) {
        injector = Guice.createInjector(module);
    }

    @Override
    public synchronized void start() throws Exception {
        steambeatWebProperties = new SteambeatWebProperties();
        setCookieParametersInContext();
        initFreemarkerConfiguration();
        setStatus();
        if (!getContext().getAttributes().get("com.steambeat.status").equals("launch")) {
            runMigrations();
        }
        final MailBuilder mailBuilder = injector.getInstance(MailBuilder.class);
        mailBuilder.setContext(getContext());
        super.start();
    }

    private void setCookieParametersInContext() {
        getContext().getAttributes().put("com.steambeat.cookie.domain", steambeatWebProperties.getCookie());
        getContext().getAttributes().put("com.steambeat.cookie.secure", steambeatWebProperties.getSecureMode());
        getContext().getAttributes().put("com.steambeat.cookie.cookiebasetime", steambeatWebProperties.getCookieBaseTime());
        getContext().getAttributes().put("com.steambeat.cookie.cookiepermanenttime", steambeatWebProperties.getCookiePermanentTime());
        getContext().getAttributes().put("com.steambeat.session.sessionbasetime", steambeatWebProperties.getSessionBaseTime());
        getContext().getAttributes().put("com.steambeat.session.sessionpermanenttime", steambeatWebProperties.getSessionPermanentTime());
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
        configuration.addAutoImport("layout", "/base/layout.ftl");
        configuration.setSharedVariable("dev", steambeatWebProperties.isDev());
        configuration.setSharedVariable("root", steambeatWebProperties.getDomain() + servletContext().getContextPath());
        configuration.setSharedVariable("buildtime", steambeatWebProperties.getBuildTime());
		configuration.setSharedVariable("userInfos", new UserInfos());
        getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private void setReadyContext() {
        getContext().getAttributes().put("com.steambeat.ready", new Boolean(steambeatWebProperties.getReadyState()));
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
    private Injector injector;
}