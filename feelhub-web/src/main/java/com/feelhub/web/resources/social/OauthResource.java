package com.feelhub.web.resources.social;

import com.feelhub.application.command.CommandBus;
import com.feelhub.domain.user.User;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.authentification.*;
import org.joda.time.*;
import org.restlet.data.Status;
import org.restlet.resource.*;
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
        final User user = getOrCreateUser(accesToken);
        authenticationManager.authenticate(AuthRequest.socialNetwork(user.getId().toString()));
        setStatus(Status.REDIRECTION_TEMPORARY);
        if (isOldUser(user)) {
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri(""));
        } else {
            setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/social/welcome"));
        }
    }

    abstract Token accessToken(String code);

    protected abstract User getOrCreateUser(Token accesToken);

    private boolean isOldUser(final User newUser) {
        return !Days.daysBetween(newUser.getCreationDate(), DateTime.now()).isLessThan(Days.ONE);
    }

    protected final AuthenticationManager authenticationManager;
    protected final CommandBus bus;
}
