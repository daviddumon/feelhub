package com.steambeat.web;

import com.steambeat.test.guice.SteambeatModuleForTest;
import com.steambeat.tools.Hiram;
import org.apache.commons.io.FilenameUtils;
import org.junit.rules.ExternalResource;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.restlet.Context;

import javax.servlet.ServletContext;
import java.io.File;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class WebApplicationTester extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        final Context context = new Context();
        context.getAttributes().put("org.restlet.ext.servlet.ServletContext", mockServletContext());
        application = new SteambeatApplication(context);
        module = new SteambeatModuleForTest();
        application.setModule(module);
        application.start();
    }

    @Override
    protected void after() {

    }

    public ServletContext mockServletContext() {
        final ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getRealPath(anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(final InvocationOnMock invocation) throws Throwable {
                File file = new File("steambeat-web/src/main/webapp");
                if (!file.exists()) {
                    file = new File("src/main/webapp");
                }
                return FilenameUtils.concat(file.getAbsolutePath(),
                        ((String) invocation.getArguments()[0]).substring(1));
            }
        });
        when(servletContext.getContextPath()).thenReturn(WebApplicationTester.SERVER_ROOT);
        when(servletContext.getContextPath()).thenReturn("/");
        return servletContext;
    }

    public ClientResource newClientResource(final String uri) {
        return new ClientResource(uri, application);
    }

    public void setHiram(final Hiram hiram) {
        module.setHiram(hiram);
    }

    private SteambeatApplication application;
    private SteambeatModuleForTest module;
    public static final String SERVER_ROOT = "http://localhost";
}
