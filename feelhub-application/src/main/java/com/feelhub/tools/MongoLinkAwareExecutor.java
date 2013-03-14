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
        delegate = Executors.newCachedThreadPool();
        this.sessionProvider = sessionProvider;
    }

    @Override
    public void execute(final Runnable runnable) {
        delegate.execute(encapsulate(runnable));
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
        delegate.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return delegate.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return delegate.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return delegate.isTerminated();
    }

    @Override
    public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
        return delegate.awaitTermination(timeout, unit);
    }

    private final SessionProvider sessionProvider;
    private final ExecutorService delegate;
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoLinkAwareExecutor.class);
}
