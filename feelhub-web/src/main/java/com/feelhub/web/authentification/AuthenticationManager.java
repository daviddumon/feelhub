package com.feelhub.web.authentification;

import com.feelhub.application.SessionService;
import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.session.CreateSessionCommand;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.Repositories;
import com.feelhub.web.tools.CookieBuilder;
import com.feelhub.web.tools.CookieManager;
import com.feelhub.web.tools.FeelhubWebProperties;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import org.joda.time.DateTime;
import org.restlet.data.Cookie;

import java.util.Map;
import java.util.UUID;

public class AuthenticationManager {

    @Inject
    public AuthenticationManager(final SessionService sessionService, final FeelhubWebProperties properties, final CookieManager cookieManager, final CommandBus commandBus) {
        this.sessionService = sessionService;
        this.properties = properties;
        this.cookieManager = cookieManager;
        this.commandBus = commandBus;
    }

    public void authenticate(final AuthRequest authRequest) {
        final User user = authenticators.get(authRequest.getAuthMethod()).authenticate(authRequest);
        CreateSessionCommand command = new CreateSessionCommand(user.getId(), new DateTime().plusSeconds(lifeTime(authRequest.isRemember())));
        ListenableFuture<UUID> result = commandBus.execute(command);
        setCookiesInResponse(authRequest.isRemember(), user, Futures.getUnchecked(result));
    }

    private void setCookiesInResponse(final boolean remember, final User user, final UUID sessionToken) {
        final CookieBuilder cookieBuilder = cookieManager.cookieBuilder();
        cookieManager.setCookie(cookieBuilder.idCookie(user));
        cookieManager.setCookie(cookieBuilder.tokenCookie(sessionToken, remember));
    }

    private int lifeTime(final boolean remember) {
        if (remember) {
            return properties.sessionPermanentTime;
        } else {
            return properties.sessionbasetime;
        }
    }

    public boolean logout() {
        final CookieBuilder cookieBuilder = cookieManager.cookieBuilder();
        final Cookie sessionCookie = cookieManager.getCookie(CookieBuilder.SESSION);
        final Cookie id = cookieManager.getCookie(CookieBuilder.ID);
        if (sessionCookie != null && id != null) {
            sessionService.deleteSession(UUID.fromString(sessionCookie.getValue()));
            cookieManager.setCookie(cookieBuilder.eraseIdCookie(id.getValue()));
            cookieManager.setCookie(cookieBuilder.eraseSessionCookie(sessionCookie.getValue()));
            return true;
        }
        return false;
    }

    public void initRequest() {
        try {
            doInit();
        } catch (Exception e) {
            CurrentUser.set(WebUser.anonymous());
        }
    }

    private void doInit() {
        final Cookie identityCookie = cookieManager.getCookie("id");
        if (identityCookie != null) {
            final User user = Repositories.users().get(identityCookie.getValue());
            if (user != null) {
                CurrentUser.set(new WebUser(user, sessionService.authentificate(user, getToken())));
                return;
            }
        }
        CurrentUser.set(WebUser.anonymous());
    }

    private UUID getToken() {
        final Cookie session = cookieManager.getCookie("session");
        if (session != null) {
            return UUID.fromString(session.getValue());
        } else {
            return null;
        }
    }

    private final SessionService sessionService;
    private final FeelhubWebProperties properties;
    private final CookieManager cookieManager;
    private CommandBus commandBus;
    private final Map<AuthMethod, Authenticator> authenticators = Maps.newConcurrentMap();

    {
        authenticators.put(AuthMethod.FEELHUB, new FeelhubAuthenticator());
        authenticators.put(AuthMethod.FACEBOOK, new FacebookAuthenticator());
    }
}

