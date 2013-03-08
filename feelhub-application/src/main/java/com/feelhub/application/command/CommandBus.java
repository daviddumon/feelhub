package com.feelhub.application.command;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.repositories.SessionProvider;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.Inject;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class CommandBus {

    @Inject
    public CommandBus(final SessionProvider sessionProvider) {
        this.sessionProvider = sessionProvider;
        this.executor = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
    }

    public <T> ListenableFuture<T> execute(final Command<T> command) {
        return executor.submit(createCallable(command));
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
                    propagateSyncEvents();
                    DomainEventBus.INSTANCE.propagate();
                }
            }

            private void propagateSyncEvents() {
                sessionProvider.start();
                try {
                    DomainEventBus.INSTANCE.propagateSync();
                } finally {
                    sessionProvider.stop();
                }
            }
        };
    }

    private final ListeningExecutorService executor;
    private final SessionProvider sessionProvider;
}
