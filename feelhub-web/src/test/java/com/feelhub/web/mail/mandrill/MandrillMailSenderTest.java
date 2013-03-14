package com.feelhub.web.mail.mandrill;

import com.feelhub.domain.admin.*;
import com.feelhub.domain.eventbus.WithDomainEvent;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class MandrillMailSenderTest {

    @Before
    public void avant() {
        final MandrillMailSender mandrillMailSender = new MandrillMailSender();
    }

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    public void incrementApiCallsCounter() {
        new MandrillMailSender() {
            @Override
            protected void sendMessage(final MandrillTemplateRequest message) {

            }
        }.send(null);

        final ApiCallEvent event = bus.lastEvent(ApiCallEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.getApi()).isEqualTo(Api.Mandrill);
        assertThat(event.getIncrement()).isEqualTo(1);
    }

    private MandrillTemplateRequest aMessage() {
        final MandrillTemplateRequest request = new MandrillTemplateRequest();
        request.template_name = "activation";
        request.message = new MandrillMessage();
        request.message.addMergeVar("ACTIVATION", "http://feelhub.com");
        request.message.subject = "Welcome to Feelhub !";
        final MandrillRecipient recipient = new MandrillRecipient();
        recipient.email = "bodysplash@gmail.com";
        recipient.name = "Jb Dusseaut";
        request.message.to.add(recipient);

        return request;
    }

}
