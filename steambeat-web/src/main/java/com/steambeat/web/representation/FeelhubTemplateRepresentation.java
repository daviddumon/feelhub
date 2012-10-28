package com.steambeat.web.representation;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import org.restlet.*;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;

import java.io.*;
import java.util.Map;

public class FeelhubTemplateRepresentation extends OutputRepresentation {

    public static FeelhubTemplateRepresentation createNew(final String template, final Context context, final Request request) {
        return createNew(template, context, MediaType.TEXT_HTML, request);
    }

    public static FeelhubTemplateRepresentation createNew(final String template, final Context context, final MediaType type, final Request request) {
        return new FeelhubTemplateRepresentation(template, context, type, request);
    }

    private FeelhubTemplateRepresentation(final String template, final Context context, final MediaType type, final Request request) {
        super(type);
        this.context = context;
        final Map<String, Object> data = Maps.newHashMap();
        representation = new org.restlet.ext.freemarker.TemplateRepresentation(template, getConfiguration(), data, type);
    }

    public FeelhubTemplateRepresentation with(final Map<String, Object> data) {
        getDataModel().putAll(data);
        return this;
    }

    public FeelhubTemplateRepresentation with(final String key, final Object value) {
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

    private final org.restlet.ext.freemarker.TemplateRepresentation representation;
    private final Context context;
}
