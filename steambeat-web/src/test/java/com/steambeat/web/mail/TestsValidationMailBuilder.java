package com.steambeat.web.mail;

import com.steambeat.domain.user.User;
import com.steambeat.test.fakeRepositories.WithFakeRepositories;
import com.steambeat.test.testFactories.TestFactories;
import com.steambeat.web.WebApplicationTester;
import org.junit.*;

import javax.mail.*;
import javax.mail.internet.MimeMessage;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.*;

public class TestsValidationMailBuilder {

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Rule
    public WebApplicationTester restlet = new WebApplicationTester();

    @Before
    public void before() {
        mailSender = mock(FakeMailSender.class);
        validationMailBuilder = new ValidationMailBuilder(mailSender);
        validationMailBuilder.setContext(restlet.getApplication().getContext());
    }

    @Test
    public void canSendAnEmail() {
        final User user = TestFactories.users().createUser("mail@mail.com");

        final MimeMessage mimeMessage = validationMailBuilder.sendValidationTo(user);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    @Test
    public void emailAsARecipient() throws MessagingException {
        final User user = TestFactories.users().createUser("mail@mail.com");

        final MimeMessage mimeMessage = validationMailBuilder.sendValidationTo(user);

        assertThat(mimeMessage.getRecipients(Message.RecipientType.TO)[0].toString(), is(user.getEmail()));
    }

    @Test
    public void validationEmailAsAFromAddress() throws MessagingException {
        final User user = TestFactories.users().createUser("mail@mail.com");

        final MimeMessage mimeMessage = validationMailBuilder.sendValidationTo(user);

        assertThat(mimeMessage.getFrom()[0].toString(), is("register@steambeat.com"));
    }

    @Test
    public void validationEmailAsASubject() throws MessagingException {
        final User user = TestFactories.users().createUser("mail@mail.com");

        final MimeMessage mimeMessage = validationMailBuilder.sendValidationTo(user);

        assertThat(mimeMessage.getSubject(), is("Welcome to Steambeat !"));
    }

    @Test
    public void validationEmailAsABody() throws IOException, MessagingException {
        final User user = TestFactories.users().createUser("mail@mail.com");

        final MimeMessage mimeMessage = validationMailBuilder.sendValidationTo(user);

        assertThat(mimeMessage.getContent(), notNullValue());
    }

    private ValidationMailBuilder validationMailBuilder;
    private FakeMailSender mailSender;
}
