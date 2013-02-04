package com.feelhub.web.mail.mandrill;

import com.feelhub.domain.admin.*;
import com.feelhub.domain.eventbus.WithDomainEvent;
import org.junit.*;

import static org.fest.assertions.Assertions.*;

public class TestsMandrillMailSender {

    @Before
    public void avant() {
        mandrillMailSender = new MandrillMailSender();
    }

    @Rule
    public WithDomainEvent bus = new WithDomainEvent();

    @Test
    @Ignore("ça envoie le mail pour de vrai")
    public void canSend() {
        final MandrillTemplateRequest request = aMessage();

        try {
            mandrillMailSender.send(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void incrementApiCallsCounter() {
        bus.capture(ApiCallEvent.class);

        new MandrillMailSender() {
            @Override
            protected void sendMessage(MandrillTemplateRequest message) {

            }
        }.send(null);

        ApiCallEvent event = bus.lastEvent(ApiCallEvent.class);
        assertThat(event).isNotNull();
        assertThat(event.getApi()).isEqualTo(Api.Mandrill);
        assertThat(event.getIncrement()).isEqualTo(1);
    }

    private MandrillTemplateRequest aMessage() {
        final MandrillTemplateRequest request = new MandrillTemplateRequest();
        request.template_name = "Activation";
        request.message = new MandrillMessage();
        request.message.addMergeVar("ACTIVATION", "http://feelhub.com");
        request.message.subject = "Welcome to Feelhub !";
        final MandrillRecipient recipient = new MandrillRecipient();
        recipient.email = "bodysplash@gmail.com";
        recipient.name = "Jb Dusseaut";
        request.message.to.add(recipient);

        return request;
    }

    private MandrillMailSender mandrillMailSender;
}
