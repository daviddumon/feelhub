package com.feelhub.web;

import org.restlet.Context;

import javax.servlet.ServletContext;

public class WebReferenceBuilder {

    public WebReferenceBuilder(final Context context) {
        this.context = context;
    }

    public String buildUri(final String uri) {
        return domain() + servletContext().getContextPath() + uri;
    }

    private String domain() {
        return context.getAttributes().get("com.feelhub.domain").toString();
    }

    private ServletContext servletContext() {
        return (ServletContext) context.getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private final Context context;
}
