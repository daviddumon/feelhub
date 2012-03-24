package com.steambeat.sitemap.web;

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
        sitemapProperties = new SitemapProperties();
        initFreemarkerConfiguration();
        //for (int i = 0; i < 100000; i++) {
        //    SitemapEntryRepository.add(new SitemapEntry("sitemap" + i, Frequency.hourly, 0.5));
        //}
        //SitemapRepository.buildAllSitemaps();
        //SitemapIndexRepository.buildAllSitemapIndexes();
        super.start();
    }

    private void initFreemarkerConfiguration() throws TemplateModelException {
        final Configuration configuration = new Configuration();
        configuration.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
        configuration.setEncoding(Locale.ROOT, "UTF-8");
        configuration.setSharedVariable("root", sitemapProperties.getRoot());
        getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    @Override
    public Restlet createInboundRoot() {
        return new SitemapRouter(getContext());
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private SitemapProperties sitemapProperties;
}
