package com.steambeat.application;

import com.steambeat.domain.user.User;
import com.steambeat.repositories.Repositories;

import java.util.UUID;

public class UserService {

    public User createUser(final String email, final String password, final String fullname, final String language) {
        CheckForExistingEmail(email);
        final User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setLanguage(language);
        Repositories.users().add(user);
        return user;
    }

    private void CheckForExistingEmail(final String email) {
        final User user = Repositories.users().get(email.toLowerCase().trim());
        if (user != null) {
            throw new EmailAlreadyUsed();
        }
    }

    public User authentificate(final String email, final String password) {
        final User user = getUser(email);
        checkUser(user);
        checkPassword(user, password);
        return user;
    }

    public User getUser(final String email) {
        User user = Repositories.users().get(email);
        if (user == null) {
            throw new BadUserException();
        }
        return user;
    }

    private void checkUser(final User user) {
        if (!user.isActive()) {
            throw new BadUserException();
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
            throw new BadUserException();
        }
        return user;
    }
}