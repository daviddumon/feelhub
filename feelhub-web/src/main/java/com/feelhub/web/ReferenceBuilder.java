package com.feelhub.web;

import com.feelhub.web.tools.FeelhubWebProperties;
import org.restlet.Context;

import javax.servlet.ServletContext;

public class ReferenceBuilder {

    public ReferenceBuilder(final Context context) {
        this.context = context;
    }

    public String buildUri(final String uri) {
        final FeelhubWebProperties feelhubWebProperties = new FeelhubWebProperties();
        return feelhubWebProperties.getDomain() + servletContext().getContextPath() + uri;
    }

    private ServletContext servletContext() {
        return (ServletContext) context.getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private final Context context;
}
