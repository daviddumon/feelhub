package com.feelhub.web;

import org.restlet.Context;

import javax.servlet.ServletContext;

public class ReferenceBuilder {

    public ReferenceBuilder(final Context context, String domain) {
        this.context = context;
		this.domain = domain;
	}

    public String buildUri(final String uri) {
		return domain + servletContext().getContextPath() + uri;
    }

    private ServletContext servletContext() {
        return (ServletContext) context.getAttributes().get("org.restlet.ext.servlet.ServletContext");
    }

    private final Context context;
	private String domain;
}
