package com.feelhub.web.authentification;

import com.feelhub.domain.user.User;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class WebUserTest {

    @Test
    public void canProvideMd5HashFromMail() {
        User user = new User();
        user.setEmail(" tOto@tatA.fr");
        WebUser webUser = new WebUser(user, true);

        String hash = webUser.getHashedEmail();

        assertThat(hash).isEqualTo("5f417fa19cab7001b4e07a126ce6ae3a");
    }
}
