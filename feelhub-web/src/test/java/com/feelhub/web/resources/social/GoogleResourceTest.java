package com.feelhub.web.resources.social;

import com.feelhub.application.command.CommandBus;
import com.feelhub.web.authentification.AuthenticationManager;
import com.feelhub.web.social.GoogleConnector;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class GoogleResourceTest {

    @Test
    public void canGetAccesToken() {
        final GoogleConnector connector = mock(GoogleConnector.class);
        final GoogleResource resource = new GoogleResource(connector, mock(AuthenticationManager.class), mock(CommandBus.class));

        resource.accessToken("code");

        verify(connector).getAccesToken("code");
    }
}
