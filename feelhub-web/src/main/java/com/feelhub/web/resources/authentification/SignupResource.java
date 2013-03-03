package com.feelhub.web.resources.authentification;

import com.feelhub.application.command.CommandBus;
import com.feelhub.application.command.user.CreateUserCommand;
import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.domain.user.BadEmail;
import com.feelhub.web.representation.ModelAndView;
import com.feelhub.web.social.FacebookConnector;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.inject.Inject;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.util.UUID;

public class SignupResource extends ServerResource {

    @Inject
    public SignupResource(final FacebookConnector connector, final CommandBus bus) {
        this.connector = connector;
        this.bus = bus;
    }

    @Get
    public ModelAndView represent() {
        return ModelAndView.createNew("signup.ftl").with("facebookUrl", connector.getUrl()).with("locales", FeelhubLanguage.availables());
    }

    @Post
    public void signup(final Form form) {
        if (!checkForm(form)) {
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return;
        }
        Futures.addCallback(bus.execute(createCommand(form)), new FutureCallback<UUID>() {
            @Override
            public void onSuccess(UUID result) {
                setStatus(Status.SUCCESS_CREATED);
            }

            @Override
            public void onFailure(Throwable t) {
                if (EmailAlreadyUsed.class.isAssignableFrom(t.getClass())) {
                    setStatus(Status.CLIENT_ERROR_CONFLICT);
                }
                if (BadEmail.class.isAssignableFrom(t.getClass())) {
                    setStatus(Status.CLIENT_ERROR_PRECONDITION_FAILED);
                }
            }
        });
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
