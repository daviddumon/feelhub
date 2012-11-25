package com.feelhub.web.mail;

import com.feelhub.domain.eventbus.DomainEventBus;
import com.feelhub.domain.eventbus.WithDomainEvent;
import com.feelhub.domain.user.Activation;
import com.feelhub.domain.user.User;
import com.feelhub.domain.user.UserConfirmationMailEvent;
import com.feelhub.repositories.fakeRepositories.WithFakeRepositories;
import com.feelhub.test.TestFactories;
import com.feelhub.web.mail.mandrill.MandrillMailSender;
import com.feelhub.web.mail.mandrill.MandrillRecipient;
import com.feelhub.web.mail.mandrill.MandrillTemplateRequest;
import com.feelhub.web.mail.mandrill.MergeVar;
import com.feelhub.web.tools.FeelhubWebProperties;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.fest.assertions.Assertions.*;
import static org.mockito.Mockito.*;

public class TestsMailWatcher {

    @Rule
    public WithDomainEvent domainEvent = new WithDomainEvent();

    @Rule
    public WithFakeRepositories repositories = new WithFakeRepositories();

    @Test
    public void canSendWelcomeMail() {
        final MandrillMailSender mailSender = mock(MandrillMailSender.class);
        final FeelhubWebProperties properties = new FeelhubWebProperties();
        properties.domain = "http://test.com";
        new MailWatcher(mailSender, properties);
        final User user = TestFactories.users().createActiveUser("test@test.com");
        final Activation activation = new Activation(user);

        DomainEventBus.INSTANCE.post(new UserConfirmationMailEvent(user, activation));

        final ArgumentCaptor<MandrillTemplateRequest> captor = ArgumentCaptor.forClass(MandrillTemplateRequest.class);
        verify(mailSender).send(captor.capture());
        final MandrillTemplateRequest request = captor.getValue();
        assertThat(request.template_name).isEqualTo("Activation");
        assertThat(request.message.to).hasSize(1);
        assertThat(request.message.subject).isEqualTo("Welcome to Feelhub !");
        final MandrillRecipient recipient = request.message.to.get(0);
        assertThat(recipient.email).isEqualTo(user.getEmail());
        assertThat(recipient.name).isEqualTo(user.getFullname());
        assertThat(request.message.global_merge_vars).contains(new MergeVar("ACTIVATION", "http://test.com/activation/" + activation.getId()));
        assertThat(request.message.tags).contains("activation");
    }
}
