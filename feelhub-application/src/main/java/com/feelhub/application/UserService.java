package com.feelhub.application;

import com.feelhub.domain.user.BadPasswordException;
import com.feelhub.domain.user.BadUserException;
import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserFactory;
import com.feelhub.repositories.Repositories;
import com.google.inject.Inject;

import java.util.UUID;

public class UserService {

	@Inject
	public UserService(final UserFactory userFactory) {
		this.userFactory = userFactory;
	}

	public User createUser(final String email, final String password, final String fullname, final String language) {
		final User user = userFactory.createUser(email, password, fullname, language);
		Repositories.users().add(user);
		return user;
	}

	public User createFromFacebook(final String id, final String email,  final String firstName, final String lastName, final String language) {
		final User user = userFactory.createFromFacebook(id, email, firstName, lastName, language);
		Repositories.users().add(user);
		return user;
	}


	public User authentificate(final String email, final String password) {
		final User user = getUser(email);
		checkUser(user);
		checkPassword(user, password);
		return user;
	}

	public User getUser(final String email) {
		final User user = Repositories.users().get(email);
		if (user == null) {
			throw new BadUserException("This user does not exist!");
		}
		return user;
	}

	private void checkUser(final User user) {
		if (!user.isActive()) {
			throw new BadUserException("Not yet activated !");
		}
	}

	private void checkPassword(final User user, final String password) {
		if (!user.checkPassword(password)) {
			throw new BadPasswordException();
		}
	}

	public String getName(final String email) {
		final User user = Repositories.users().get(email.toLowerCase().trim());
		if (user != null) {
			return user.getFullname();
		}
		return "";
	}

	public User getUserForSecret(final UUID secret) {
		final User user = Repositories.users().forSecret(secret);
		if (user == null) {
			throw new BadUserException("This user does not exist!");
		}
		return user;
	}

	private final UserFactory userFactory;
}
