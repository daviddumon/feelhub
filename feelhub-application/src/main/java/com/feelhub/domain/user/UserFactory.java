package com.feelhub.domain.user;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;

public class UserFactory {

    public User createUser(final String email, final String password, final String fullname, final String language) {
        final User user = commonUser(email, email, fullname, language);
        user.setPassword(password);
        DomainEventBus.INSTANCE.post(new UserCreatedEvent(user));
        return user;
    }

    public User createFromFacebook(final String facebookId, final String email, final String firstName, final String lastName, final String language, String token) {
        final User user = commonUser(UserIds.facebook(facebookId), email, firstName + " " + lastName, language);
        user.addToken(new SocialToken(SocialNetwork.FACEBOOK, token));
        user.activate();
        return user;
    }

    private User commonUser(final String id, final String email, final String fullname, final String language) {
        checkForExistingEmail(email);
        final User user = new User(id);
        user.setEmail(email);
        user.setFullname(fullname);
        user.setLanguageCode(FeelhubLanguage.forString(language).getCode());
        return user;
    }

    private void checkForExistingEmail(final String email) {
        final User user = Repositories.users().forEmail(email.toLowerCase().trim());
        if (user != null) {
            throw new EmailAlreadyUsed();
        }
    }
}
