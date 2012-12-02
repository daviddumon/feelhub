package com.feelhub.domain.user;

import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;

public class UserTestFactory {

    public User createFakeUser(final String email) {
        final FakeUser user = new FakeUser();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname("full name");
        user.setLanguage(FeelhubLanguage.reference());
        Repositories.users().add(user);
        return user;
    }

    public User createFakeUser(final String email, final String fullName) {
        final FakeUser user = new FakeUser();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname(fullName);
        user.setLanguage(FeelhubLanguage.reference());
        Repositories.users().add(user);
        return user;
    }

    public User createFakeActiveUser(final String email) {
        final FakeUser user = new FakeUser();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname("full name");
        user.setLanguage(FeelhubLanguage.reference());
        user.activate();
        Repositories.users().add(user);
        return user;
    }

    public User createActiveUser(final String email) {
        final User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setFullname("full name");
        user.setLanguage(FeelhubLanguage.reference());
        user.activate();
        Repositories.users().add(user);
        return user;
    }
}
