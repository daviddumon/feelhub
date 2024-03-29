package com.feelhub.web.mail;

import com.feelhub.application.mail.FeelhubMail;
import com.feelhub.domain.user.*;
import com.feelhub.domain.user.activation.*;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.WebApplicationTester;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class MailBuilderTest {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        mailSender = new FakeMailSender();
        mailBuilder = new MailBuilder(mailSender);
        mailBuilder.setContext(restlet.getApplication().getContext());
    }

    @Test
    public void canSendAnEmailOnActivation() {
        final User user = TestFactories.users().createFakeUser("mail@mail.com");
        final Activation activation = new Activation(user);

        mailBuilder.onActivationCreated(new ActivationCreatedEvent(user.getId(), activation.getId()));

        assertThat(mailSender.messages()).hasSize(1);
        final FeelhubMail mail = mailSender.messages().get(0);
        assertThat(mail.to()).isEqualTo(user.getEmail());
        assertThat(mail.from()).isEqualTo(FeelhubMail.DEFAULT_FROM);
        assertThat(mail.subject()).isEqualTo("Welcome to Feelhub !");
        assertThat(mail.textContent()).contains("activate");
    }

    @Test
    public void canSendAnEmailOnUserCreated() {
        final User user = TestFactories.users().createFakeActiveUser("mail@mail.com");

        mailBuilder.onUserCreated(new UserCreatedEvent(user));

        assertThat(mailSender.messages()).hasSize(1);
        final FeelhubMail mail = mailSender.messages().get(0);
        assertThat(mail.to()).isEqualTo(user.getEmail());
        assertThat(mail.from()).isEqualTo(FeelhubMail.DEFAULT_FROM);
        assertThat(mail.subject()).isEqualTo("Welcome to Feelhub !");
        assertThat(mail.textContent()).contains("Thank you for registering with Feelhub");
    }

    @Test
    public void dontSendAnEmailOnUserCreatedIfUserInactive() {
        final User user = TestFactories.users().createFakeUser("mail@mail.com");

        mailBuilder.onUserCreated(new UserCreatedEvent(user));

        assertThat(mailSender.messages()).isEmpty();
    }

    private MailBuilder mailBuilder;
    private FakeMailSender mailSender;
}
