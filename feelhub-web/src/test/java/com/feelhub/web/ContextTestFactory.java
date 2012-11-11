package com.feelhub.web;

import com.feelhub.web.resources.authentification.SessionsResource;
import org.apache.commons.io.FilenameUtils;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;

import javax.servlet.ServletContext;
import java.io.File;

import static org.mockito.Mockito.*;

public final class ContextTestFactory {

    private ContextTestFactory() {
    }

    public static Context buildContext() {
        final Context result = new Context();
        result.getAttributes().put("org.restlet.ext.servlet.ServletContext", mockServletContext());
        result.getAttributes().put("com.feelhub.domain", "https://thedomain");
        return result;
    }

    private static ServletContext mockServletContext() {
        final ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getRealPath(anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(final InvocationOnMock invocation) throws Throwable {
                File file = new File("feelhub-web/src/main/webapp");
                if (!file.exists()) {
                    file = new File("src/main/webapp");
                }
                return FilenameUtils.concat(file.getAbsolutePath(),
                        ((String) invocation.getArguments()[0]).substring(1));
            }
        });
        when(servletContext.getContextPath()).thenReturn("/");
        return servletContext;
    }

    public static void initResource(SessionsResource resource) {
        Request request = new Request();
        resource.init(buildContext(), request, new Response(request));
        ;
    }
}
