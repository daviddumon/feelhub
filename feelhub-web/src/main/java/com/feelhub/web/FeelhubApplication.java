package com.feelhub.web;

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.feelhub.analytic.AnalyticWorkersModule;
import com.feelhub.domain.DomainWorkersModule;
import com.feelhub.repositories.Repositories;
import com.feelhub.sitemap.amazon.S3SitemapsRepository;
import com.feelhub.sitemap.application.SitemapsRepository;
import com.feelhub.web.authentification.UserInfos;
import com.feelhub.web.filter.*;
import com.feelhub.web.mail.MailBuilder;
import com.feelhub.web.social.FacebookConnector;
import com.feelhub.web.status.FeelhubStatusService;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.feelhub.web.update.UpdateRouter;
import com.google.inject.*;
import freemarker.template.*;
import org.restlet.*;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.servlet.ServletContext;
import java.util.Locale;
import java.util.logging.*;

public class FeelhubApplication extends Application {

    public FeelhubApplication(final Context context) {
        super(context);
        setStatusService(new FeelhubStatusService());
        final java.util.logging.Logger rootLogger = LogManager.getLogManager().getLogger("");
        final Handler[] handlers = rootLogger.getHandlers();
        for (final Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }
        SLF4JBridgeHandler.install();
    }

    public void initializeGuice(final Module module) {
        injector = Guice.createInjector(Stage.PRODUCTION, module, new DomainWorkersModule(), new AnalyticWorkersModule());
    }

    @Override
    public synchronized void start() throws Exception {
        feelhubWebProperties = injector.getInstance(FeelhubWebProperties.class);
        setContextVariables();
        initFreemarkerConfiguration();
        final MailBuilder mailBuilder = injector.getInstance(MailBuilder.class);
        mailBuilder.setContext(getContext());
        Repositories.initialize(injector.getInstance(Repositories.class));
        SitemapsRepository.initialize(new S3SitemapsRepository(new AmazonS3Client(new ClasspathPropertiesFileCredentialsProvider())));
        super.start();
    }

    private void setContextVariables() {
        getContext().getAttributes().put("com.feelhub.status", feelhubWebProperties.status);
        getContext().getAttributes().put("com.feelhub.domain", feelhubWebProperties.domain);
    }

    private void initFreemarkerConfiguration() throws TemplateModelException {
        final Configuration configuration = new Configuration();
        configuration.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
        configuration.setEncoding(Locale.ROOT, "UTF-8");
        configuration.addAutoImport("head", "/base/head.ftl");
        configuration.addAutoImport("flow", "/base/flow.ftl");
        configuration.addAutoImport("base", "/base.ftl");
        configuration.setSharedVariable("dev", feelhubWebProperties.dev);
        configuration.setSharedVariable("root", feelhubWebProperties.domain + servletContext().getContextPath());
        configuration.setSharedVariable("cookie", feelhubWebProperties.cookie);
        configuration.setSharedVariable("buildtime", feelhubWebProperties.buildtime);
        configuration.setSharedVariable("userInfos", new UserInfos());
        final FacebookConnector facebookConnector = injector.getInstance(FacebookConnector.class);
        configuration.setSharedVariable("facebookUrl", facebookConnector.getUrl());
        configuration.setSharedVariable("googleUrl", new WebReferenceBuilder(getContext()).buildUri("/social/google-signup"));
        getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
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
            final IdentityFilter filter = injector.getInstance(IdentityFilter.class);
            filter.setContext(getContext());
            filter.setNext(new UpdateRouter(getContext(), injector));
            router.attach(filter);
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
        filter.setNext(new FeelhubRouter(getContext(), injector));
        return filter;
    }

    private FeelhubWebProperties feelhubWebProperties;
    private Injector injector;
}