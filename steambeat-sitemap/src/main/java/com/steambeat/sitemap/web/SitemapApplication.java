package com.steambeat.sitemap.web;

import com.steambeat.sitemap.domain.*;
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
        final SitemapIndex sitemapIndex = RobotsFile.INSTANCE.newSitemapIndex();
        sitemapIndex.newSitemap();
        sitemapIndex.newSitemap();
        sitemapIndex.newSitemap();
        sitemapIndex.newSitemap();
        initFreemarkerConfiguration();
        super.start();
    }

    private void initFreemarkerConfiguration() throws TemplateModelException {
        final Configuration configuration = new Configuration();
        configuration.setServletContextForTemplateLoading(servletContext(), "WEB-INF/templates");
        configuration.setEncoding(Locale.ROOT, "UTF-8");
        getContext().getAttributes().put("org.freemarker.Configuration", configuration);
    }

    @Override
    public Restlet createInboundRoot() {
        return new SitemapRouter(getContext());
    }

    private ServletContext servletContext() {
        return (ServletContext) getContext().getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }
}
