package com.feelhub.web.mail;

import com.feelhub.domain.user.Activation;
import com.feelhub.domain.user.User;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.WebApplicationTester;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class TestsMailBuilder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        mailSender = mock(FakeMailSender.class);
        mailBuilder = new MailBuilder(mailSender);
        mailBuilder.setContext(restlet.getApplication().getContext());
    }

    @Test
    public void canSendAnEmail() {
        final User user = TestFactories.users().createFakeUser("mail@mail.com");
        Activation activation = new Activation(user);

        final MimeMessage mimeMessage = mailBuilder.sendValidationTo(user, activation);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void emailAsARecipient() throws MessagingException {
        final User user = TestFactories.users().createFakeUser("mail@mail.com");
        Activation activation = new Activation(user);

        final MimeMessage mimeMessage = mailBuilder.sendValidationTo(user, activation);

        assertThat(mimeMessage.getRecipients(Message.RecipientType.TO)[0].toString(), is(user.getEmail()));
    }

    @Test
    public void activationEmailAsAFromAddress() throws MessagingException {
        final User user = TestFactories.users().createFakeUser("mail@mail.com");
        Activation activation = new Activation(user);

        final MimeMessage mimeMessage = mailBuilder.sendValidationTo(user, activation);

        assertThat(mimeMessage.getFrom()[0].toString(), is("register@feelhub.com"));
    }

    @Test
    public void activationEmailAsASubject() throws MessagingException {
        final User user = TestFactories.users().createFakeUser("mail@mail.com");
        Activation activation = new Activation(user);

        final MimeMessage mimeMessage = mailBuilder.sendValidationTo(user, activation);

        assertThat(mimeMessage.getSubject(), is("Welcome to Feelhub !"));
    }

    @Test
    public void activationEmailAsABody() throws IOException, MessagingException {
        final User user = TestFactories.users().createFakeUser("mail@mail.com");
        Activation activation = new Activation(user);

        final MimeMessage mimeMessage = mailBuilder.sendValidationTo(user, activation);

        assertThat(mimeMessage.getContent(), notNullValue());
    }

    private MailBuilder mailBuilder;
    private FakeMailSender mailSender;
}
