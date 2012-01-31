package com.steambeat.web;

import com.steambeat.tools.SteambeatWebProperties;
import org.restlet.Context;

import javax.servlet.ServletContext;

public class ReferenceBuilder {

    public ReferenceBuilder(final Context context) {
        this.context = context;
    }

    public String buildUri(final String uri) {
        final SteambeatWebProperties steambeatWebProperties = new SteambeatWebProperties();
        return "http://" + steambeatWebProperties.getDomain() + servletContext().getContextPath() + uri;
    }

    private ServletContext servletContext() {
        return (ServletContext) context.getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private final Context context;
}
