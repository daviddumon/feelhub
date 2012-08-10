package com.steambeat.web.filter;

import org.restlet.*;

public class FakeOpenSessionInViewFilter extends OpenSessionInViewFilter {

    public FakeOpenSessionInViewFilter() {
        super(null);
    }

    @Override
    protected void doStart() {
    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        return CONTINUE;
    }

    @Override
    protected void afterHandle(final Request request, final Response response) {

    }
}
