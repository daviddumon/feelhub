package com.feelhub.web.resources.authentification;

import com.feelhub.application.command.user.activation.ConfirmActivationCommand;
import com.feelhub.web.WebReferenceBuilder;
import com.feelhub.web.dto.FeelhubMessage;
import com.feelhub.web.tools.CookieManager;
import com.google.inject.Inject;
import org.restlet.data.Status;
import org.restlet.resource.*;

import java.util.UUID;

public class ActivationResource extends ServerResource {

    @Inject
    public ActivationResource(final CookieManager cookieManager) {
        this.cookieManager = cookieManager;
    }

    @Get
    public void confirm() {
        final UUID secret = UUID.fromString(getRequestAttributes().get("secret").toString());
        final ConfirmActivationCommand confirmActivationCommand = new ConfirmActivationCommand(secret);
        try {
            confirmActivationCommand.execute();
            cookieManager.setCookie(cookieManager.cookieBuilder().messageCookie(FeelhubMessage.getActivationMessage()));
        } catch (Exception e) {
            cookieManager.setCookie(cookieManager.cookieBuilder().messageCookie(FeelhubMessage.getErrorMessage()));
        }
        setStatus(Status.REDIRECTION_TEMPORARY);
        setLocationRef(new WebReferenceBuilder(getContext()).buildUri("/"));
    }

    private final CookieManager cookieManager;
}
