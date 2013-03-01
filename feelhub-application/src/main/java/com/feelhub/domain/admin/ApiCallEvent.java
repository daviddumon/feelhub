package com.feelhub.domain.admin;

import com.feelhub.domain.eventbus.DomainEvent;

public class ApiCallEvent extends DomainEvent {

    public static ApiCallEvent bingSearch() {
        return new ApiCallEvent(Api.BingSearch);
    }

    public static ApiCallEvent alchemy() {
        return new ApiCallEvent(Api.Alchemy);
    }

    public static DomainEvent mandrill() {
        return new ApiCallEvent(Api.Mandrill);
    }

    public static DomainEvent microsoftTranslate(final int increment) {
        return new ApiCallEvent(Api.MicrosoftTranslate, increment);
    }

    public static DomainEvent sendGrid() {
        return new ApiCallEvent(Api.SendGrid);
    }

    private ApiCallEvent(final Api api) {
        this(api, 1);
    }

    private ApiCallEvent(final Api api, final int increment) {
        this.api = api;
        this.increment = increment;
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

    private final Api api;
    private final int increment;
}
