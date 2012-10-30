package com.feelhub.tools;

import org.restlet.*;
import org.restlet.data.Protocol;

public final class Clients {

    public static Client create() {
        final Client client = new Client(Context.getCurrent(), Protocol.HTTP);
        client.setConnectTimeout(msTimeOut);
        return client;
    }

    public static void stop(final Client client) {
        try {
            client.stop();
        } catch (Exception e) {
        }
    }

    private Clients() {

    }

    final static private int msTimeOut = 3000;
}
