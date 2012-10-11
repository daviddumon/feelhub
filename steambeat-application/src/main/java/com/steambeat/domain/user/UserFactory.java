package com.steambeat.domain.user;

import com.steambeat.domain.eventbus.DomainEventBus;
import com.steambeat.domain.thesaurus.SteambeatLanguage;

public class UserFactory {

    public User createUser(final String email, final String password, final String fullname, final String language) {
        final User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setLanguageCode(SteambeatLanguage.forString(language).getCode());
        DomainEventBus.INSTANCE.post(new UserCreatedEvent(user));
        return user;
    }
}
