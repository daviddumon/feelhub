package com.feelhub.application.command;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.SessionProvider;
import com.google.common.util.concurrent.*;

import javax.inject.Inject;
import java.util.concurrent.*;

public class CommandBus {

    @Inject
    public CommandBus(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

    public <T> ListenableFuture<T> execute(final Command<T> command) {
        return EXECUTOR_SERVICE.submit(createCallable(command));
    }

    private <T> Callable<T> createCallable(final Command<T> command) {
        return new Callable<T>() {
            @Override
            public T call() throws Exception {
                sessionProvider.start();
                try {
                    return command.execute();
                } finally {
                    sessionProvider.stop();
                    DomainEventBus.INSTANCE.propagate();
                }
            }
        };
    }

    private final SessionProvider sessionProvider;
    public static final ListeningExecutorService EXECUTOR_SERVICE = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
}
