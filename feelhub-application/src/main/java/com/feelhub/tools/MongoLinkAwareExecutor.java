package com.feelhub.tools;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.SessionProvider;
import com.google.inject.Inject;
import org.apache.log4j.Logger;

import java.util.concurrent.*;

public class MongoLinkAwareExecutor implements Executor {

    @Inject
    public MongoLinkAwareExecutor(final SessionProvider sessionProvider) {
        executorService = Executors.newCachedThreadPool();
        this.sessionProvider = sessionProvider;
    }

    @Override
    public void execute(final Runnable runnable) {
        executorService.execute(encapsulate(runnable));
    }

    private Runnable encapsulate(final Runnable runnable) {
        return new Runnable() {
            @Override
            public void run() {
                LOGGER.debug("Starting session for async service");
                sessionProvider.start();
                try {
                    runnable.run();
                } finally {
                    LOGGER.debug("Stopping session for async service");
                    DomainEventBus.INSTANCE.propagate();
                    sessionProvider.stop();
                }
            }
        };
    }

    private final SessionProvider sessionProvider;
    private final ExecutorService executorService;
    private static final Logger LOGGER = Logger.getLogger(MongoLinkAwareExecutor.class);
}
