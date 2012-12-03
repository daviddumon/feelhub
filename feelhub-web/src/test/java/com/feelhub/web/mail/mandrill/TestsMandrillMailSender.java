package com.feelhub.web.mail.mandrill;

import org.junit.*;

public class TestsMandrillMailSender {

    @Before
    public void avant() {
        mandrillMailSender = new MandrillMailSender();
    }

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
