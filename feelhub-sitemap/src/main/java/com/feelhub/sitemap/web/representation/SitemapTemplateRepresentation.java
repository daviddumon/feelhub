package com.feelhub.sitemap.web.representation;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.OutputRepresentation;

import java.io.*;
import java.util.Map;

public class SitemapTemplateRepresentation extends OutputRepresentation {

    public static SitemapTemplateRepresentation createNew(final String template, final Context context) {
        return createNew(template, context, MediaType.TEXT_HTML);
    }

    public static SitemapTemplateRepresentation createNew(final String template, final Context context, final MediaType type) {
        return new SitemapTemplateRepresentation(template, context, type);
    }

    public SitemapTemplateRepresentation(final String template, final Context context, final MediaType type) {
        super(type);
        this.context = context;
        final Map<String, Object> data = Maps.newHashMap();
        representation = new TemplateRepresentation(template, getConfiguration(), data, type);
    }

    public SitemapTemplateRepresentation with(final Map<String, Object> data) {
        getDataModel().putAll(data);
        return this;
    }

    public SitemapTemplateRepresentation with(final String key, final Object value) {
        getDataModel().put(key, value);
        return this;
    }

    public Map<String, Object> getDataModel() {
        return (Map<String, Object>) representation.getDataModel();
    }

    @Override
    public void write(final OutputStream arg0) throws IOException {
        representation.write(arg0);
    }

    public <T> T getData(final String key) {
        return (T) getDataModel().get(key);
    }

    private Configuration getConfiguration() {
        return (Configuration) context.getAttributes().get("org.freemarker.Configuration");
    }

    private final TemplateRepresentation representation;
    private final Context context;
}