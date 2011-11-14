package com.steambeat.tools;

import org.junit.Test;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

public class TestsSteambeatProperties {

    @Test
    public void canGetDomain() {
        final SteambeatProperties properties = new SteambeatProperties();

        final String domain = properties.getDomain();

        assertThat(domain, is("thedomain"));
    }

    @Test
    public void canGetHiramAddress() {
        final SteambeatProperties properties = new SteambeatProperties();

        String address = properties.getHiramAddress();

        assertThat(address, is("http://localhost:6162/hiram"));
    }
}
