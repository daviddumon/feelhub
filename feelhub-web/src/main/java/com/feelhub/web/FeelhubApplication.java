package com.feelhub.web;

import com.feelhub.web.authentification.UserInfos;
import com.feelhub.web.filter.*;
import com.feelhub.web.update.UpdateRouter;
import com.feelhub.web.migration.MigrationRunner;
import com.feelhub.web.migration.web.*;
import com.feelhub.web.status.FeelhubStatusService;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.inject.*;
import freemarker.template.*;
import org.restlet.*;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import org.restlet.service.TaskService;

import javax.servlet.ServletContext;
import java.util.Locale;

public class FeelhubApplication extends Application {

    public FeelhubApplication(final Context context) {
        super(context);
        setStatusService(new FeelhubStatusService());
    }

    public void initializeGuice(final Module module) {
        injector = Guice.createInjector(module);
    }

    @Override
    public synchronized void start() throws Exception {
        feelhubWebProperties = injector.getInstance(FeelhubWebProperties.class);
        initFreemarkerConfiguration();
        setContextVariables();
        if (!getContext().getAttributes().get("com.feelhub.status").equals("update")) {
            runMigrations();
        }
        super.start();
    }

    private void setContextVariables() {
        getContext().getAttributes().put("com.feelhub.status", feelhubWebProperties.status);
        getContext().getAttributes().put("com.feelhub.domain", feelhubWebProperties.domain);
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
        configuration.addAutoImport("flow", "/base/flow.ftl");
        configuration.addAutoImport("noflow", "/base/noflow.ftl");
        configuration.setSharedVariable("dev", feelhubWebProperties.dev);
        configuration.setSharedVariable("root", feelhubWebProperties.domain + servletContext().getContextPath());
        configuration.setSharedVariable("buildtime", feelhubWebProperties.buildtime);
        configuration.setSharedVariable("userInfos", new UserInfos());
        getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private void setReadyContext() {
        getContext().getAttributes().put("com.feelhub.ready", new Boolean(feelhubWebProperties.ready));
    }

    @Override
    public Restlet createInboundRoot() {
        final Router router = new Router(getContext());
        router.attach("/static", new Directory(getContext(), "war:///static"));
        setCorrectRouter(router);
        return router;
    }

    private void setCorrectRouter(final Router router) {
        if (getContext().getAttributes().get("com.feelhub.status").equals("update")) {
            //final IdentityFilter filter = injector.getInstance(IdentityFilter.class);
            //filter.setContext(getContext());
            //filter.setNext(new LaunchRouter(getContext(), injector));
            //router.attach(filter);
            router.attach(new UpdateRouter(getContext(), injector));
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
        filter.setFeelhubRouter(new FeelhubRouter(getContext(), injector));
        filter.setMigrationRouter(new MigrationRouter(getContext(), injector));
        filter.setContext(getContext());
        filter.setNext(new FeelhubRouter(getContext(), injector));
        return filter;
    }

    private FeelhubWebProperties feelhubWebProperties;
    private Injector injector;
}