package com.feelhub.application.mail.factory;

import com.feelhub.application.mail.FeelhubMail;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;


public class TestsValidationMailFactory {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canGetMail() {
        User user = TestFactories.users().createActiveUser("toto@example.fr");
        user.setFullname("a full name");

        FeelhubMail mail = new ValidationMailFactory().build(user, "anUriForTest");

        assertThat(mail).isNotNull();
        assertThat(mail.to()).isEqualTo("toto@example.fr");
        assertThat(mail.subject()).isEqualTo("Welcome to Feelhub !");
        assertThat(mail.content()).contains("anUriForTest");
        assertThat(mail.content()).contains("a full name");
    }
}
