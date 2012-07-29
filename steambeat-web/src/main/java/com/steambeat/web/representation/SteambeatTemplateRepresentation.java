package com.steambeat.web.representation;

import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import org.restlet.Context;
import org.restlet.data.MediaType;
import org.restlet.ext.freemarker.TemplateRepresentation;
import org.restlet.representation.OutputRepresentation;

import java.io.*;
import java.util.Map;

public class SteambeatTemplateRepresentation extends OutputRepresentation {

    public static SteambeatTemplateRepresentation createNew(final String template, final Context context) {
        return createNew(template, context, MediaType.TEXT_HTML);
    }

    public static SteambeatTemplateRepresentation createNew(final String template, final Context context, final MediaType type) {
        return new SteambeatTemplateRepresentation(template, context, type);
    }

    public SteambeatTemplateRepresentation(final String template, final Context context, final MediaType type) {
        super(type);
        this.context = context;
        final Map<String, Object> data = Maps.newHashMap();
        representation = new TemplateRepresentation(template, getConfiguration(), data, type);
        setIdentityInRepresentation(context);
    }

    private void setIdentityInRepresentation(final Context context) {
        ((Map<String, Object>) representation.getDataModel()).put("identity", context.getAttributes().get("com.steambeat.identity"));
    }

    public SteambeatTemplateRepresentation with(final Map<String, Object> data) {
        getDataModel().putAll(data);
        return this;
    }

    public SteambeatTemplateRepresentation with(final String key, final Object value) {
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
