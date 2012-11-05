package com.feelhub.web.authentification;

import com.feelhub.application.*;
import com.feelhub.domain.session.Session;
import com.feelhub.domain.user.User;
import com.feelhub.web.resources.authentification.AuthMethod;
import com.feelhub.web.tools.*;
import com.google.inject.Inject;
import org.joda.time.DateTime;
import org.restlet.data.Cookie;

import java.util.UUID;

public class AuthenticationManager {

    @Inject
    public AuthenticationManager(final UserService userService, final SessionService sessionService, FeelhubWebProperties properties, CookieManager cookieManager) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.properties = properties;
        this.cookieManager = cookieManager;
    }

    public void authenticate(final AuthRequest authRequest) {
        User user = extractUser(authRequest);
        final Session session = sessionService.createSession(user, new DateTime().plusSeconds(lifeTime(authRequest.isRemember())));
        setCookiesInResponse(authRequest.isRemember(), user, session);
    }

    private User extractUser(final AuthRequest authRequest) {
        if (authRequest.getAuthMethod() == AuthMethod.FEELHUB) {
            return userService.authentificate(authRequest.getEmail(), authRequest.getPassword());
        }
        return userService.getUser(authRequest.getEmail());
    }

    private void setCookiesInResponse(final boolean remember, final User user, final Session session) {
        final CookieBuilder cookieBuilder = cookieManager.cookieBuilder();
        cookieManager.setCookie(cookieBuilder.idCookie(user));
        cookieManager.setCookie(cookieBuilder.tokenCookie(session, remember));
    }

    private int lifeTime(final boolean remember) {
        if (remember) {
            return properties.sessionPermanentTime;
        } else {
            return properties.sessionbasetime;
        }
    }

    public boolean logout() {
        CookieBuilder cookieBuilder = cookieManager.cookieBuilder();
        final Cookie sessionCookie = cookieManager.getCookies().getFirst(CookieBuilder.SESSION);
        final Cookie id = cookieManager.getCookies().getFirst(CookieBuilder.ID);
        if (sessionCookie != null && id != null) {
            sessionService.deleteSession(UUID.fromString(sessionCookie.getValue()));
            cookieManager.setCookie(cookieBuilder.eraseIdCookie(id.getValue()));
            cookieManager.setCookie(cookieBuilder.eraseSessionCookie(sessionCookie.getValue()));
            return true;
        }
        return false;
    }

    public void initRequest() {
        final Cookie identityCookie = cookieManager.getCookie("id");
        try {
            if (identityCookie != null) {
                User user = userService.getUser(identityCookie.getValue());
                CurrentUser.set(new WebUser(user, sessionService.authentificate(user, getToken())));
            } else {
                CurrentUser.set(WebUser.anonymous());
            }
        } catch (Exception e) {
            CurrentUser.set(WebUser.anonymous());
        }
    }

    private UUID getToken() {
        final Cookie session = cookieManager.getCookie("session");
        if (session != null) {
            return UUID.fromString(session.getValue());
        } else {
            return null;
        }
    }

    private final UserService userService;
    private final SessionService sessionService;
    private FeelhubWebProperties properties;
    private CookieManager cookieManager;
}
