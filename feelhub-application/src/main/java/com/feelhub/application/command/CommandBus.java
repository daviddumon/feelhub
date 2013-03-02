package com.feelhub.application.command;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class CommandBus {

    public CommandBus(final ExecutorService executor) {
        this.executor = MoreExecutors.listeningDecorator(executor);
    }

    public <T> ListenableFuture<T> execute(final Command<T> command) {
        return executor.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return command.execute();
            }
        });
    }

    private final ListeningExecutorService executor;
}
