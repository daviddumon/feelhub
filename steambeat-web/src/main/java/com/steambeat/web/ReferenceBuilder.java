package com.steambeat.web;

import com.steambeat.tools.SteambeatProperties;
import org.restlet.Context;

import javax.servlet.ServletContext;

public class ReferenceBuilder {

    public ReferenceBuilder(Context context) {
        this.context = context;
    }

    public String buildUri(String uri) {
        final SteambeatProperties props = new SteambeatProperties();
        return "http://" + props.getDomain() + servletContext().getContextPath() + uri;
    }

    private ServletContext servletContext() {
        return (ServletContext) context.getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private Context context;
}
