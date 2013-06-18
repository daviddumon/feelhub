package com.feelhub.web.resources.social;

import com.feelhub.application.command.CommandBus;
import com.feelhub.domain.feeling.SentimentValue;
import com.feelhub.domain.user.User;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.*;
import com.feelhub.web.dto.FeelhubMessage;
import com.feelhub.web.tools.CookieManager;
import org.joda.time.*;
import org.restlet.data.Status;
import org.restlet.resource.*;
import org.scribe.model.Token;

public abstract class OauthResource extends ServerResource {

    public OauthResource(final AuthenticationManager authenticationManager, final CommandBus bus, final CookieManager cookieManager) {
        this.authenticationManager = authenticationManager;
        this.bus = bus;
        this.cookieManager = cookieManager;
    }

    @Get
    public void returns() {
        final String code = getQuery().getFirstValue("code");
        final Token accesToken = accessToken(code);
        final User user = getOrCreateUser(accesToken);
        authenticationManager.authenticate(AuthRequest.socialNetwork(user.getId().toString()));
        setStatus(Status.REDIRECTION_TEMPORARY);
        if (isOldUser(user)) {
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/"));
        } else {
            cookieManager.setCookie(cookieManager.cookieBuilder().messageCookie(getWelcomeMessage()));
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/"));
        }
    }

    private FeelhubMessage getWelcomeMessage() {
        final FeelhubMessage feelhubMessage = new FeelhubMessage();
        feelhubMessage.setFeeling(SentimentValue.good.toString());
        feelhubMessage.setSecondTimer(3);
        feelhubMessage.setText("Welcome to Feelhub! We hope you will enjoy it :)");
        return feelhubMessage;
    }

    abstract Token accessToken(String code);

    protected abstract User getOrCreateUser(Token accesToken);

    private boolean isOldUser(final User newUser) {
        return !Hours.hoursBetween(newUser.getCreationDate(), DateTime.now()).isLessThan(Hours.ONE);
    }

    protected final AuthenticationManager authenticationManager;
    protected final CommandBus bus;
    private CookieManager cookieManager;
}
