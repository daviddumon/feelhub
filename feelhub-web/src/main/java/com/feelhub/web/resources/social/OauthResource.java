package com.feelhub.web.resources.social;

import com.feelhub.application.command.CommandBus;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.AuthRequest;
import com.feelhub.web.authentification.AuthenticationManager;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.scribe.model.Token;

public abstract class OauthResource extends ServerResource {

    public OauthResource(final AuthenticationManager authenticationManager, final CommandBus bus) {
        this.authenticationManager = authenticationManager;
        this.bus = bus;
    }

    @Get
    public void returns() {
        final String code = getQuery().getFirstValue("code");
        final Token accesToken = accessToken(code);
        final com.feelhub.domain.user.User user = getOrCreateUser(accesToken);
        authenticationManager.authenticate(AuthRequest.socialNetwork(user.getId().toString()));
        setStatus(Status.REDIRECTION_TEMPORARY);
        if (isOldUser(user)) {
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri(""));
        } else {
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/social/welcome"));
        }
    }

    abstract Token accessToken(String code);

    protected abstract com.feelhub.domain.user.User getOrCreateUser(Token accesToken);

    private boolean isOldUser(final com.feelhub.domain.user.User newUser) {
        return !Days.daysBetween(newUser.getCreationDate(), DateTime.now()).isLessThan(Days.ONE);
    }

    protected final AuthenticationManager authenticationManager;
    protected final CommandBus bus;
}
