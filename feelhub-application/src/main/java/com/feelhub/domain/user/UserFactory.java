package com.feelhub.domain.user;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.session.EmailAlreadyUsed;
import com.feelhub.domain.thesaurus.FeelhubLanguage;
import com.feelhub.repositories.Repositories;
import com.google.common.base.Optional;

public class UserFactory {

    public User createUser(final String email, final String password, final String fullname, final String language) {
        final User user = commonUser(email, fullname);
        user.setLanguage(FeelhubLanguage.fromCode(language));
        user.setPassword(password);
        DomainEventBus.INSTANCE.post(new UserCreatedEvent(user));
        return user;
    }

    public User createFromFacebook(final String facebookId, final String email, final String firstName, final String lastName, final String language, final String token) {
        return createFromSocialNetwork(facebookId, email, firstName, lastName, language, token, SocialNetwork.FACEBOOK);
    }

    public User createFromGoogle(final String googleId, final String email, final String firstName, final String lastName, final String language, final String token) {
        return createFromSocialNetwork(googleId, email, firstName, lastName, language, token, SocialNetwork.GOOGLE);
    }

    private User createFromSocialNetwork(final String googleId, final String email, final String firstName, final String lastName, final String language, final String token, final SocialNetwork socialNetwork) {
        final User user = commonUser(email, firstName + " " + lastName);
        user.setLanguage(FeelhubLanguage.fromCode(language));
        user.addSocialAuth(new SocialAuth(socialNetwork, googleId, token));
        user.activate();
        DomainEventBus.INSTANCE.post(new UserCreatedEvent(user));
        return user;
    }

    private User commonUser(final String email, final String fullname) {
        checkForExistingEmail(email);
        final User user = new User();
        user.setEmail(email);
        user.setFullname(fullname);
        return user;
    }

    private void checkForExistingEmail(final String email) {
        final Optional<User> user = Repositories.users().forEmail(email.toLowerCase().trim());
        if (user.isPresent()) {
            throw new EmailAlreadyUsed();
        }
    }
}
