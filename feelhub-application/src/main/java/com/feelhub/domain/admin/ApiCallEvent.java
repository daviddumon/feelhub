package com.feelhub.domain.admin;

import com.feelhub.domain.eventbus.DomainEvent;

public class ApiCallEvent extends DomainEvent{

    public static ApiCallEvent bingSearch() {
        return new ApiCallEvent(Api.BingSearch);
    }

    public static ApiCallEvent alchemy() {
        return new ApiCallEvent(Api.Alchemy);
    }

    private ApiCallEvent(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

    public int getIncrement() {
        return increment;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date.toString());
        stringBuilder.append(" - ");
        stringBuilder.append(getClass().getSimpleName() + " ");
        return stringBuilder.toString();
    }

    private Api api;
    private int increment = 1;

}
