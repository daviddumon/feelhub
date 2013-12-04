package com.feelhub.web.filter;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.*;
import org.restlet.*;
import org.restlet.routing.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class OpenSessionInViewFilter extends Filter {

    @Inject
    public OpenSessionInViewFilter(final SessionProvider provider) {
        this.provider = provider;
    }

    @Override
    public void start() throws Exception {
        if (!isStarted()) {
            doStart();
        }
        super.start();
    }

    protected void doStart() {
        Repositories.initialize(new MongoRepositories(provider));
    }

    @Override
    protected int beforeHandle(final Request request, final Response response) {
        provider.start();
        return CONTINUE;
    }

    @Override
    protected void afterHandle(final Request request, final Response response) {
        provider.stop();
        DomainEventBus.INSTANCE.propagate();
    }

    @Override
    public void stop() throws Exception {
        LOGGER.info("############## Stoping session");
        super.stop();
        provider.close();
        LOGGER.info("############## Session stopped");
    }

    private final SessionProvider provider;
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenSessionInViewFilter.class);
}
