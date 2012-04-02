package com.steambeat.sitemap.web;

import com.google.inject.*;
import com.steambeat.sitemap.application.SitemapScheduler;
import com.steambeat.sitemap.guice.SitemapModule;
import com.steambeat.sitemap.tools.SitemapProperties;
import freemarker.template.*;
import org.restlet.*;

import javax.servlet.ServletContext;
import java.util.Locale;

public class SitemapApplication extends Application {

    public SitemapApplication(final Context context) {
        super(context);
    }

    @Override
    public synchronized void start() throws Exception {
        sitemapScheduler = injector.getInstance(SitemapScheduler.class);
        sitemapProperties = new SitemapProperties();
        initFreemarkerConfiguration();
        sitemapScheduler.initialize();
        sitemapScheduler.run();
        super.start();
    }

    private void initFreemarkerConfiguration() throws TemplateModelException {
        final Configuration configuration = new Configuration();
        configuration.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
        configuration.setEncoding(Locale.ROOT, "UTF-8");
        configuration.setSharedVariable("root", sitemapProperties.getRoot());
        getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    public void setModule(final Module module) {
        injector = Guice.createInjector(module);
    }

    @Override
    public Restlet createInboundRoot() {
        return new SitemapRouter(getContext());
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private SitemapProperties sitemapProperties;
    private Injector injector = Guice.createInjector(new SitemapModule());
    private SitemapScheduler sitemapScheduler;
}
