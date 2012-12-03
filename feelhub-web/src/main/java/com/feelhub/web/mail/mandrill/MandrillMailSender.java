package com.feelhub.web.mail.mandrill;

import com.feelhub.tools.Clients;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.restlet.*;
import org.restlet.data.*;

public class MandrillMailSender {

    public MandrillMailSender() {
    }

    public void send(final MandrillTemplateRequest message) {
        message.key = KEY;
        message.async = false;
        message.message.from_email = FROM;
        message.message.addMergeVar("CURRENT_YEAR", String.valueOf(DateTime.now().year().get()));
        message.message.addMergeVar("COMPANY", "Feelhub");
        final Request request = new Request(Method.POST, URI + "/" + VERSION + "/messages/send-template.json");
        final String json = new Gson().toJson(message);
        request.setEntity(json, MediaType.APPLICATION_JSON);
        final Client client = Clients.create();
        final Response response = client.handle(request);
        Clients.stop(client);
        if (!response.getStatus().isSuccess()) {
            throw new MandrillException(response.getStatus(), response.getEntityAsText());
        }
    }


    private static final String KEY = "c00ab9f2-b4bf-4fe7-8953-a2dae1e4bf10";
    private static final String URI = "https://mandrillapp.com/api";
    private static final String VERSION = "1.0";
    private static final String FROM = "register@feelhub.com";
}
