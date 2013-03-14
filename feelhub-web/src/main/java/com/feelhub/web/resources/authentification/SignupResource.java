package com.feelhub.web.resources.authentification;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.user.CreateUserCommand;
import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.BadEmail;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.social.FacebookConnector;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.*;
import com.google.inject.Inject;
import org.restlet.data.*;
import org.restlet.resource.*;

import java.util.UUID;

public class SignupResource extends ServerResource {

    @Inject
    public SignupResource(final FacebookConnector connector, final CommandBus bus) {
        this.connector = connector;
        this.bus = bus;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("signup.ftl")
                .with("facebookUrl", connector.getUrl())
                .with("locales", FeelhubLanguage.availables())
                .with("preferedLanguage", getPreferedLanguage().getPrimaryTag());
    }

    private Language getPreferedLanguage() {
        if (getRequest().getClientInfo().getAcceptedLanguages().isEmpty()) {
            return Language.ENGLISH;
        }
        return getRequest().getClientInfo().getAcceptedLanguages().get(0).getMetadata();
    }

    @Post
    public void signup(final Form form) {
        if (!checkForm(form)) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return;
        }
        ListenableFuture<UUID> result = bus.execute(createCommand(form));
        try {
            Futures.getUnchecked(result);
            setStatus(Status.SUCCESS_CREATED);
        } catch (Exception e) {
            if (EmailAlreadyUsed.class.isAssignableFrom(Throwables.getRootCause(e).getClass())) {
                setStatus(Status.CLIENT_ERROR_CONFLICT);
                return;
            }
            if (BadEmail.class.isAssignableFrom(Throwables.getRootCause(e).getClass())) {
                setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED);
                return;
            }
            Throwables.propagate(e);
        }
    }

    private CreateUserCommand createCommand(Form form) {
        final String email = form.getFirstValue("email");
        final String password = form.getFirstValue("password");
        final String fullname = form.getFirstValue("fullname");
        final String language = form.getFirstValue("language");
        return new CreateUserCommand(email, password, fullname, language);
    }

    private boolean checkForm(final Form form) {
        return form.getQueryString().contains("email")
                && form.getQueryString().contains("password")
                && form.getQueryString().contains("fullname")
                && form.getQueryString().contains("language");
    }

    private final FacebookConnector connector;
    private CommandBus bus;
}
