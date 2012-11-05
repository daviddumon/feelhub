package com.feelhub.web.tools;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class FeelhubWebProperties {

    @Inject
    @Named("sessionpermanenttime")
    public int sessionPermanentTime;

    @Inject
    @Named("sessionbasetime")
    public int sessionbasetime;

    @Inject
    @Named("cookiepermanenttime")
    public int cookiePermanentTime;

    @Inject
    @Named("cookiebasetime")
    public int cookieBaseTime;

    @Inject
    @Named("secureMode")
    public boolean secureMode;

    @Inject
    @Named("dev")
    public boolean dev;

    @Inject
    @Named("cookie")
    public String cookie;

    @Inject
    @Named("status")
    public String status;

    @Inject
    @Named("ready")
    public String ready;

    @Inject
    @Named("buildtime")
    public String buildtime;

    @Inject
    @Named("sitemapBuilder")
    public String sitemapBuilder;

    @Inject
    @Named("domain")
    public String domain;

    @Inject
    @Named("facebook.appId")
    public String facebookAppId;

    @Inject
    @Named("facebook.appSecret")
    public String facebookAppSecret;
}
