package com.steambeat.web.resources.authentification;

import com.google.inject.Inject;
import com.restfb.types.User;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.scribe.model.Token;

public class FacebookLoginResource extends ServerResource {

	@Inject
	public FacebookLoginResource(FacebookConnector connector) {
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
