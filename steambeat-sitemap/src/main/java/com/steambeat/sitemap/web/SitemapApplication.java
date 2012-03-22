package com.steambeat.sitemap.web;

import freemarker.template.Configuration;
import org.restlet.*;

import javax.servlet.ServletContext;
import java.util.Locale;

public class SitemapApplication extends Application {

    public SitemapApplication(final Context context) {
        super(context);
    }

    @Override
    public synchronized void start() throws Exception {
        initFreemarkerConfiguration();
        super.start();
    }

    private void initFreemarkerConfiguration() {
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
