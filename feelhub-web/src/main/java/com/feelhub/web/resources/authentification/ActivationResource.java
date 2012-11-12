package com.feelhub.web.resources.authentification;

import com.feelhub.application.ActivationService;
import com.feelhub.web.representation.ModelAndView;
import com.google.inject.Inject;
import org.restlet.resource.*;

import java.util.UUID;

public class ActivationResource extends ServerResource {

    @Inject
    public ActivationResource(final ActivationService activationService) {
        this.activationService = activationService;
    }

    @Get
    public ModelAndView confirm() {
        final UUID secret = UUID.fromString(getRequestAttributes().get("secret").toString());
        activationService.confirm(secret);
        return ModelAndView.createNew("activation.ftl");
    }


    private ActivationService activationService;
}
