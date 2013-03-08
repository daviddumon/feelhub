package com.feelhub.tools;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.SessionProvider;
import com.google.inject.Inject;
import org.joda.time.DateTime;
import org.slf4j.*;

import java.util.List;
import java.util.concurrent.*;

public class MongoLinkAwareExecutor extends AbstractExecutorService {

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
                final Long timestamp = new DateTime().getMillis();
                LOGGER.debug("Starting session for async service : {}", timestamp);
                sessionProvider.start();
                try {
                    runnable.run();
                } finally {
                    LOGGER.debug("Stopping session for async service : {}", timestamp);
                    sessionProvider.stop();
                    DomainEventBus.INSTANCE.propagate();
                }
            }
        };
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executorService.awaitTermination(timeout, unit);
    }

    private final SessionProvider sessionProvider;
    private final ExecutorService executorService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoLinkAwareExecutor.class);
}
