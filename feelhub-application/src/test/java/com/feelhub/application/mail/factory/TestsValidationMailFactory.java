package com.feelhub.application.mail.factory;

import com.feelhub.application.mail.FeelhubMail;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.*;

import static org.fest.assertions.Assertions.*;


public class TestsValidationMailFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetMail() {
        final User user = TestFactories.users().createActiveUser("toto@example.fr");
        user.setFullname("a full name");

        final FeelhubMail mail = new ValidationMailFactory().build(user, "anUriForTest");

        assertThat(mail).isNotNull();
        assertThat(mail.to()).isEqualTo("toto@example.fr");
        assertThat(mail.subject()).isEqualTo("Welcome to Feelhub !");
        assertThat(mail.htmlContent()).contains("anUriForTest</a>");
        assertThat(mail.htmlContent()).contains("<p>Dear a full name");
        assertThat(mail.textContent()).contains("anUriForTest");
        assertThat(mail.textContent()).doesNotContain("anUriForTest</a>");
    }
}
