package com.feelhub.web.resources.social;

import com.feelhub.web.authentification.FacebookConnector;
import com.google.inject.Inject;
import com.restfb.types.User;
import org.restlet.data.MediaType;
import org.restlet.representation.*;
import org.restlet.resource.*;
import org.scribe.model.Token;

public class FacebookResource extends ServerResource {

    @Inject
    public FacebookResource(FacebookConnector connector) {
        this.connector = connector;
    }

    @Get
    public Representation facebookReturn() {
        final String facebookCode = getQuery().getFirstValue("code");
        final Token accesToken = connector.getAccesToken(facebookCode);
        final User user = connector.getUser(accesToken);
        final StringBuilder builder = new StringBuilder();
        builder.append("User name :" + user.getName());
        builder.append("User id :" + user.getId());
        builder.append("User link :" + user.getLink());
        builder.append("User first name :" + user.getFirstName());
        builder.append("User last name :" + user.getLastName());
        builder.append("User username :" + user.getUsername());
        builder.append("User locale :" + user.getLocale());
        return new StringRepresentation(builder.toString(), MediaType.TEXT_PLAIN);
    }

    private FacebookConnector connector;
}
