package com.feelhub.domain.admin;

import com.feelhub.domain.eventbus.DomainEvent;
import com.google.common.base.Objects;

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
        return Objects.toStringHelper(this).add("Date", date).toString();
    }

    private final Api api;
    private final int increment;
}
