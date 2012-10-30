package com.feelhub.domain.user;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.thesaurus.FeelhubLanguage;

public class UserFactory {

    public User createUser(final String email, final String password, final String fullname, final String language) {
        final User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setLanguageCode(FeelhubLanguage.forString(language).getCode());
        DomainEventBus.INSTANCE.post(new UserConfirmationMailEvent(user));
        return user;
    }
}
