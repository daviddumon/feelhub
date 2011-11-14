package com.steambeat.test.guice;

import com.steambeat.web.OpenSessionInViewFilter;
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
    protected int beforeHandle(Request request, Response response) {
        return CONTINUE;
    }

    @Override
    protected void afterHandle(Request request, Response response) {

    }
}
