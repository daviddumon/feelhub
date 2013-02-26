package com.feelhub.application.command;


public interface Command<T> {

    public T execute();
}
