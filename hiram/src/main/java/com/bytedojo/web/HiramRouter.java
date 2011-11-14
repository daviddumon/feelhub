package com.bytedojo.web;

import org.restlet.Context;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

public class HiramRouter extends Router {

    public HiramRouter(final Context context) {
        super(context);
        createRoot();
    }

    private void createRoot() {
        Directory directory = new Directory(getContext(), "file:///hiram/sitemaps");
        attach("/", directory);
    }
}
