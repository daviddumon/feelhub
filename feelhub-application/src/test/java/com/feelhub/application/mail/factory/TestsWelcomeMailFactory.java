package com.feelhub.application.mail.factory;

import com.feelhub.application.mail.FeelhubMail;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;


public class TestsWelcomeMailFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetMail() {
        final User user = TestFactories.users().createActiveUser("toto@example.fr");

        final FeelhubMail mail = new WelcomeMailFactory().build(user);

        assertThat(mail).isNotNull();
        assertThat(mail.to()).isEqualTo("toto@example.fr");
        assertThat(mail.subject()).isEqualTo("Welcome to Feelhub !");
        assertThat(mail.htmlContent()).contains("<p>Thank you for registering with Feelhub");
        assertThat(mail.htmlContent()).contains("<p>Best regards,</p>");
        assertThat(mail.textContent()).contains("Thank you for registering with Feelhub");
    }
}
